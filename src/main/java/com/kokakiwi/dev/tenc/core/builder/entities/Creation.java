package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Creation extends Assignment
{
    public final static String  TYPE = "Creation";
    private final Function.Type dataType;
    
    public Creation(Function.Type dataType)
    {
        super(TYPE, dataType.getName(), Token.ASSIGN);
        this.dataType = dataType;
    }
    
    public Function.Type getDataType()
    {
        return dataType;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(dataType);
        
        return sb.toString();
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start creation"));
        }
        if (children.size() > 0)
        {
            lines.addAll(super.generate(context));
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.lookahead(0).code() == Token.DATA_TYPE_NAME
                && reader.lookahead(1).code() == Token.IDENTIFIER)
        {
            final Token type = reader.lookahead(0);
            final Token name = reader.lookahead(1);
            
            reader.accept(Token.DATA_TYPE_NAME);
            reader.accept(Token.IDENTIFIER);
            
            final Creation creation = new Creation(new Function.Type(
                    type.getMatched(), name.getMatched()));
            
            if (reader.lookahead(0).code() == Token.ASSIGN)
            {
                reader.accept(Token.ASSIGN);
                
                Expression.accept(creation, reader);
            }
            
            node.addChild(creation);
            
            result = true;
        }
        
        return result;
    }
}
