package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Assignment extends AbstractSyntaxNode
{
    public final static String TYPE     = "Assignment";
    public final static String regToUse = "X";
    
    private final String       ident;
    
    public Assignment(String ident)
    {
        this(TYPE, ident);
    }
    
    public Assignment(String type, String ident)
    {
        super(type);
        this.ident = ident;
    }
    
    public String getIdent()
    {
        return ident;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(ident);
        
        return sb.toString();
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        Context exprContext = new Context(context);
        exprContext.setValue("__result", regToUse);
        
        for (AbstractSyntaxNode child : children)
        {
            lines.addAll(child.generate(exprContext));
        }
        
        int offset = context.getOffset(ident);
        lines.add("SET I, SP");
        if (offset != 0)
        {
            lines.add("ADD I, " + offset);
        }
        lines.add("SET [I], " + regToUse);
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.lookahead(0).code() == Token.IDENTIFIER
                && reader.lookahead(1).code() == Token.ASSIGN)
        {
            reader.accept(Token.IDENTIFIER);
            reader.accept(Token.ASSIGN);
            
            final Token assigned = reader.lookahead(-2);
            
            final Assignment assignment = new Assignment(assigned.getMatched());
            
            Expression.accept(assignment, reader);
            
            node.addChild(assignment);
            
            result = true;
        }
        
        if (MemSet.accept(node, reader))
        {
            result = true;
        }
        
        return result;
    }
}
