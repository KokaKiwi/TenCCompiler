package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Term extends AbstractSyntaxNode
{
    public final static String TYPE     = "Term";
    public final static String regToUse = "Y";
    
    public Term()
    {
        super(TYPE);
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start term"));
        }
        
        if (children.size() == 1)
        {
            Factor factor = (Factor) children.get(0);
            lines.addAll(factor.generate(context));
        }
        else
        {
            String oldReg = (String) context.getValue("__result");
            context.setValue("__result", regToUse);
            
            String oldOp = (String) context.getValue("__op");
            
            boolean first = true;
            for (int i = 0; i < children.size(); i++)
            {
                AbstractSyntaxNode child = children.get(i);
                if (child instanceof Operation)
                {
                    Operation op = (Operation) child;
                    
                    if (i == 0 || i == children.size() - 1)
                    {
                        Context.error("Unexpected operator!");
                    }
                    String operation = null;
                    if (op.getOpType() == Token.TIMES)
                    {
                        operation = "MUL";
                    }
                    else if (op.getOpType() == Token.DIVIDE)
                    {
                        operation = "DIV";
                    }
                    else if (op.getOpType() == Token.MOD)
                    {
                        operation = "MOD";
                    }
                    else
                    {
                        Context.error("Unknown expr op " + op.getOpType());
                    }
                    if (first)
                    {
                        if (children.get(i - 1) instanceof Factor
                                && children.get(i + 1) instanceof Factor)
                        {
                            Factor a = (Factor) children.get(i - 1);
                            Factor b = (Factor) children.get(i + 1);
                            context.setValue("__op", "SET");
                            lines.addAll(a.generate(context));
                            context.setValue("__op", operation);
                            lines.addAll(b.generate(context));
                            context.setValue("__op", null);
                            first = false;
                        }
                        else
                        {
                            Context.error("Expected operation factors");
                        }
                    }
                    else
                    {
                        if (children.get(i + 1) instanceof Factor)
                        {
                            Factor a = (Factor) children.get(i + 1);
                            context.setValue("__op", operation);
                            lines.addAll(a.generate(context));
                            context.setValue("__op", null);
                        }
                    }
                }
            }
            
            lines.add(new Instruction(new Opcode(oldOp)).first(
                    new RegisterAccess(oldReg)).second(
                    new RegisterAccess(regToUse)));
            context.setValue("__result", oldReg);
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        final Term term = new Term();
        
        if (Factor.accept(term, reader))
        {
            while (reader.accept(Token.TIMES) || reader.accept(Token.DIVIDE)
                    || reader.accept(Token.MOD))
            {
                final Token op = reader.lookahead(-1);
                final Operation operation = new Operation(op.code());
                term.addChild(operation);
                Factor.accept(term, reader);
            }
            node.addChild(term);
            
            result = true;
        }
        
        return result;
    }
}
