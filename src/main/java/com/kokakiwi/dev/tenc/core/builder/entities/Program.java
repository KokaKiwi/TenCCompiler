package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.AssemblyLine;

public class Program extends AbstractSyntaxNode
{
    public final static String TYPE = "Program";
    
    public Program()
    {
        super(TYPE);
    }
    
    public void build(TokenReader reader)
    {
        while (Function.accept(this, reader))
        {
            
        }
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        for (final AbstractSyntaxNode child : children)
        {
            if (child instanceof Function)
            {
                final Function function = (Function) child;
                context.setFunction(function.getReturnType().getName(),
                        function);
            }
        }
        
        for (final AbstractSyntaxNode child : children)
        {
            lines.addAll(child.generate(context));
        }
        
        return lines;
    }
    
}
