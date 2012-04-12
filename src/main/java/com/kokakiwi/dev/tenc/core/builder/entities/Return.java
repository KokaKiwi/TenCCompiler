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
                Expression expr = (Expression) children.get(0);
                
                Context exprContext = new Context(context);
                if (expr.getChildren().size() == 1)
                {
                    exprContext.setValue("__result", "A");
                    lines.addAll(expr.generate(exprContext));
                    function.generateExitLines(lines);
                }
                else
                {
                    exprContext.setValue("__result", "X");
                    lines.addAll(expr.generate(exprContext));
                    lines.add(new Instruction(Opcode.SET).first(
                            new RegisterAccess("A")).second(
                            new RegisterAccess("X")));
                    function.generateExitLines(lines);
                }
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
