package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
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
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        if (children.size() == 1)
        {
            Factor factor = (Factor) children.get(0);
            lines.addAll(factor.generate(context));
            lines.add("SET " + regToUse + ", " + Factor.regToUse);
        }
        else
        {
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
                            lines.addAll(a.generate(context));
                            lines.add("SET " + regToUse + ", "
                                    + Factor.regToUse);
                            lines.addAll(b.generate(context));
                            lines.add(operation + " " + regToUse + ", "
                                    + Factor.regToUse);
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
                            lines.addAll(a.generate(context));
                            lines.add(operation + " " + regToUse + ", "
                                    + Factor.regToUse);
                        }
                    }
                }
            }
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
