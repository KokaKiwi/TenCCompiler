package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Return extends AbstractSyntaxNode
{
    public final static String TYPE     = "Return";
    public final static String regToUse = "A";
    
    public Return()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        Function function = context.getFunction(context.getName());
        if (function.getReturnType().getType().equalsIgnoreCase("void"))
        {
            if (children.size() > 0)
            {
                Context.error("cannot return value from void function");
            }
        }
        else if (function.getReturnType().getType().equalsIgnoreCase("int"))
        {
            if (children.size() > 0)
            {
                for (AbstractSyntaxNode child : children)
                {
                    lines.addAll(child.generate(context));
                }
                int offset = context.getOffset("__return");
                
                lines.add("ADD SP, " + offset);
                lines.add("SET PC, POP");
            }
            else
            {
                Context.error("Must return value!");
            }
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.accept(Token.RETURN))
        {
            final Return expr = new Return();
            
            Expression.accept(expr, reader);
            
            node.addChild(expr);
            
            result = true;
        }
        
        return result;
    }
}
