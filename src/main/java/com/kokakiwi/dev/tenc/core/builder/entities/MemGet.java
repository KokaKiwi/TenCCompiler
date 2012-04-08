package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class MemGet extends Factor
{
    public final static String TYPE = "MemGet";
    
    public MemGet()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        List<String> lines = Lists.newLinkedList();
        
        Expression address = (Expression) children.get(0);
        
        lines.add(";Getting RAM");
        lines.add(";Calc address");
        Context addressContext = new Context(context);
        addressContext.setValue("__result", "J");
        lines.addAll(address.generate(addressContext));
        lines.add(";Return value");
        lines.add("SET " + regToUse + ", [J]");
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        MemGet memGet = new MemGet();
        if (reader.accept(Token.OPEN_SQUARE))
        {
            if (Expression.accept(memGet, reader)
                    && reader.accept(Token.CLOSE_SQUARE))
            {
                node.addChild(memGet);
                
                result = true;
            }
            else
            {
                reader.error("MemGet expected!");
            }
        }
        
        return result;
    }
    
}
