package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class FunctionCall extends Factor
{
    public final static String TYPE = "FunctionCall";
    
    private final String       name;
    
    public FunctionCall(String name)
    {
        super(TYPE);
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(name);
        
        return sb.toString();
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        Function function = context.getFunction(name);
        if (function == null)
        {
            Context.error("Tried to call non-existant function " + name);
        }
        
        lines.add("SET PUSH, 0");
        lines.add("SET I, SP");
        
        context.setValue("__result", regToUse);
        for (AbstractSyntaxNode child : children)
        {
            lines.addAll(child.generate(context));
            lines.add("SET PUSH, " + regToUse);
        }
        
        lines.add("SET [I], PC");
        lines.add("ADD [I], 3");
        lines.add("SET PC, " + String.format(Function.FUNCTION_MANGLE, name));
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.lookahead(0).code() == Token.IDENTIFIER
                && reader.lookahead(1).code() == Token.OPEN_PAREN)
        {
            final Token funcName = reader.lookahead(0);
            
            final FunctionCall functionCall = new FunctionCall(
                    funcName.getMatched());
            
            reader.accept(Token.IDENTIFIER);
            reader.accept(Token.OPEN_PAREN);
            
            boolean searchArgs = true;
            while (searchArgs)
            {
                if (!Expression.accept(functionCall, reader)
                        || !reader.accept(Token.COMMA))
                {
                    searchArgs = false;
                }
            }
            reader.expect(Token.CLOSE_PAREN, "function call args close");
            
            node.addChild(functionCall);
            
            result = true;
        }
        
        return result;
    }
}
