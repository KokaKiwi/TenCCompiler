package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class MemSet extends AbstractSyntaxNode
{
    public final static String TYPE     = "MemSet";
    public final static String regToUse = "X";
    
    public MemSet()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        List<String> lines = Lists.newLinkedList();
        
        Expression address = (Expression) children.get(0);
        Expression value = (Expression) children.get(1);

        lines.add(";Setting RAM");
        lines.add(";Calc value");
        context.setValue("__result", regToUse);
        lines.addAll(value.generate(context));
        lines.add(";Calc address");
        context.setValue("__result", "J");
        lines.addAll(address.generate(context));
        lines.add(";Setting value");
        lines.add("SET [J], " + regToUse);
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        MemSet memSet = new MemSet();
        if (reader.accept(Token.OPEN_SQUARE))
        {
            if (Expression.accept(memSet, reader)
                    && reader.accept(Token.CLOSE_SQUARE)
                    && reader.accept(Token.ASSIGN))
            {
                Expression.accept(memSet, reader);
                
                node.addChild(memSet);
                
                result = true;
            }
            else
            {
                reader.error("MemSet expected!");
            }
        }
        
        return result;
    }
}
