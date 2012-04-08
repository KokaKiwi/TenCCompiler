package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class ConditionFactor extends AbstractSyntaxNode
{
    public final static String TYPE     = "ConditionFactor";
    public final static String regToUse = "B";
    
    public ConditionFactor()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        Expression e1 = (Expression) children.get(0);
        Operation op = (Operation) children.get(1);
        Expression e2 = (Expression) children.get(2);
        
        context.setValue("__result", "J");
        lines.addAll(e1.generate(context));
        context.setValue("__result", regToUse);
        lines.addAll(e2.generate(context));
        
        String falsy;
        switch (op.getOpType())
        {
            case Token.GREATER:
                lines.add("IFG J, " + regToUse);
                lines.add(context.getValue("condFactorInstruction"));
                break;
            
            case Token.GREATEREQ:
                lines.add("IFG J, " + regToUse);
                lines.add(context.getValue("condFactorInstruction"));
                lines.add("IFE J, " + regToUse);
                lines.add(context.getValue("condFactorInstruction"));
                break;
            
            case Token.LESSTHAN:
                falsy = context.getUniqueId("lessfalse");
                lines.add("IFG J, " + regToUse);
                lines.add("SET PC, " + falsy);
                lines.add("IFE J, " + regToUse);
                lines.add("SET PC, " + falsy);
                lines.add(context.getValue("condFactorInstruction"));
                lines.add(":" + falsy);
                break;
            
            case Token.LESSEQ:
                falsy = context.getUniqueId("lesseqfalse");
                lines.add("IFG J, " + regToUse);
                lines.add("SET PC, " + falsy);
                lines.add(context.getValue("condFactorInstruction"));
                lines.add(":" + falsy);
                break;
            
            case Token.EQUAL:
                lines.add("IFE J, " + regToUse);
                lines.add(context.getValue("condFactorInstruction"));
                break;
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        final ConditionFactor factor = new ConditionFactor();
        
        if (Expression.accept(factor, reader)
                && (reader.accept(Token.GREATER)
                        || reader.accept(Token.GREATEREQ)
                        || reader.accept(Token.LESSTHAN)
                        || reader.accept(Token.LESSEQ) || reader
                            .accept(Token.EQUAL)))
        {
            final Token typeToken = reader.lookahead(-1);
            final Operation operation = new Operation(typeToken.code());
            factor.addChild(operation);
            if (!Expression.accept(factor, reader))
            {
                reader.error("Syntax error: Expected second expression");
            }
            
            node.addChild(factor);
            result = true;
        }
        
        return result;
    }
}
