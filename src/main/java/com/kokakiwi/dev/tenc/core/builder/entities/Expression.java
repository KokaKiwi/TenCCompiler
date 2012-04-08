package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Expression extends Factor
{
    public final static String TYPE = "Expression";
    
    public Expression()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        if (context.getValue("__result") == null)
        {
            context.setValue("__result", Factor.regToUse);
        }
        String reg = context.getValue("__result");
        
        if (children.size() == 1)
        {
            Term term = (Term) children.get(0);
            lines.addAll(term.generate(context));
            lines.add("SET " + reg + ", " + Term.regToUse);
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
                    if (op.getOpType() == Token.PLUS)
                    {
                        operation = "ADD";
                    }
                    else if (op.getOpType() == Token.MINUS)
                    {
                        operation = "SUB";
                    }
                    else
                    {
                        Context.error("Unknown expr op " + op.getOpType());
                    }
                    if (first)
                    {
                        if (children.get(i - 1) instanceof Term
                                && children.get(i + 1) instanceof Term)
                        {
                            Term a = (Term) children.get(i - 1);
                            Term b = (Term) children.get(i + 1);
                            lines.addAll(a.generate(context));
                            lines.add("SET " + reg + ", " + Term.regToUse);
                            lines.addAll(b.generate(context));
                            lines.add(operation + " " + reg + ", "
                                    + Term.regToUse);
                            first = false;
                        }
                        else
                        {
                            Context.error("Expected operation terms");
                        }
                    }
                    else
                    {
                        if (children.get(i + 1) instanceof Term)
                        {
                            Term a = (Term) children.get(i + 1);
                            lines.addAll(a.generate(context));
                            lines.add(operation + " " + reg + ", "
                                    + Term.regToUse);
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
        
        final Expression expression = new Expression();
        if (Term.accept(expression, reader))
        {
            while (reader.accept(Token.PLUS) || reader.accept(Token.MINUS))
            {
                final Token op = reader.lookahead(-1);
                final Operation operation = new Operation(op.code());
                expression.addChild(operation);
                Term.accept(expression, reader);
            }
            node.addChild(expression);
            
            result = true;
        }
        
        return result;
    }
    
}
