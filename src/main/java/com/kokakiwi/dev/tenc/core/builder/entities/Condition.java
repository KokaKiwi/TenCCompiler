package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Condition extends AbstractSyntaxNode
{
    public final static String TYPE = "Condition";
    
    public Condition()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        if (children.size() == 1)
        {
            context.setValue("condTermInstruction",
                    context.getValue("condInstruction"));
            ConditionTerm term = (ConditionTerm) children.get(0);
            lines.addAll(term.generate(context));
        }
        else
        {
            boolean first = true;
            String trueLabel = context.getUniqueId("or-chain-true");
            String falseLabel = context.getUniqueId("or-chain-false");
            context.setValue("condTermInstruction", "SET PC, " + trueLabel);
            
            for (int i = 0; i < children.size(); i++)
            {
                AbstractSyntaxNode child = children.get(i);
                if (child instanceof Operation)
                {
                    Operation op = (Operation) child;
                    if (i == 0 || i == children.size() - 1)
                    {
                        Context.error("Unexpected ||");
                    }
                    if (op.getOpType() != Token.OR)
                    {
                        Context.error("Unexpected op " + op.getOpType());
                    }
                    
                    if (first)
                    {
                        ConditionFactor a = (ConditionFactor) children
                                .get(i - 1);
                        ConditionFactor b = (ConditionFactor) children
                                .get(i + 1);
                        lines.addAll(a.generate(context));
                        lines.addAll(b.generate(context));
                        first = false;
                    }
                    else
                    {
                        ConditionFactor factor = (ConditionFactor) children
                                .get(i + 1);
                        lines.addAll(factor.generate(context));
                        lines.add("SET PC, " + falseLabel);
                    }
                    lines.add(":" + trueLabel);
                    lines.add(context.getValue("condInstruction"));
                    lines.add(":" + falseLabel);
                }
            }
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        final Condition condition = new Condition();
        if (ConditionTerm.accept(condition, reader))
        {
            while (reader.accept(Token.OR))
            {
                final Token op = reader.lookahead(-1);
                final Operation operation = new Operation(op.code());
                condition.addChild(operation);
                ConditionTerm.accept(condition, reader);
            }
            node.addChild(condition);
            
            result = true;
        }
        
        return result;
    }
}