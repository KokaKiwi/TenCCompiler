package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
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
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start return"));
        }
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
                
                lines.add(new Instruction(Opcode.ADD).first(
                        new RegisterAccess("SP")).second(new Value(offset)));
                lines.add(new Instruction(Opcode.SET).first(
                        new RegisterAccess("PC")).second(
                        new RegisterAccess("POP")));
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
