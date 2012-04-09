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
    
    private final boolean      expectedResult;
    
    public ConditionFactor(boolean expectedResult)
    {
        super(TYPE);
        this.expectedResult = expectedResult;
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        AbstractSyntaxNode comp = children.get(0);
        
        if (comp instanceof Boolean)
        {
            Boolean bool = (Boolean) comp;
            if (bool.isValue())
            {
                if (expectedResult)
                {
                    lines.add(context.getValue("condFactorInstruction"));
                }
            }
            else
            {
                if (!expectedResult)
                {
                    lines.add(context.getValue("condFactorInstruction"));
                }
            }
        }
        else if (comp instanceof Expression)
        {
            Expression e1 = (Expression) comp;
            
            if (children.size() == 1)
            {
                context.setValue("__result", Factor.regToUse);
                
                lines.addAll(e1.generate(context));
                if (expectedResult)
                {
                    lines.add("IFE 1, " + Identifier.regToUse);
                }
                else
                {
                    lines.add("IFN 1, " + Identifier.regToUse);
                }
                lines.add(context.getValue("condFactorInstruction"));
            }
            else
            {
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
                        lines.add(context.getValue("condFactorInstruction"));
                        lines.add(":" + falsy);
                        break;
                    
                    case Token.LESSEQ:
                        falsy = context.getUniqueId("lesseqfalse");
                        lines.add("IFG J, " + regToUse);
                        lines.add("SET PC, " + falsy);
                        lines.add("IFE J, " + regToUse);
                        lines.add("SET PC, " + falsy);
                        lines.add(context.getValue("condFactorInstruction"));
                        lines.add(":" + falsy);
                        break;
                    
                    case Token.EQUAL:
                        lines.add("IFE J, " + regToUse);
                        lines.add(context.getValue("condFactorInstruction"));
                        break;
                    
                    case Token.NOTEQUAL:
                        lines.add("IFN J, " + regToUse);
                        lines.add(context.getValue("condFactorInstruction"));
                        break;
                }
            }
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        boolean expectedResult = true;
        if (reader.accept(Token.ANTI))
        {
            expectedResult = false;
        }
        
        final ConditionFactor factor = new ConditionFactor(expectedResult);
        
        if (Boolean.accept(factor, reader))
        {
            node.addChild(factor);
            result = true;
        }
        else if (Expression.accept(factor, reader))
        {
            if (reader.accept(Token.GREATER) || reader.accept(Token.GREATEREQ)
                    || reader.accept(Token.LESSTHAN)
                    || reader.accept(Token.LESSEQ)
                    || reader.accept(Token.EQUAL)
                    || reader.accept(Token.NOTEQUAL))
            {
                final Token typeToken = reader.lookahead(-1);
                final Operation operation = new Operation(typeToken.code());
                factor.addChild(operation);
                if (!Expression.accept(factor, reader))
                {
                    reader.error("Syntax error: Expected second expression");
                }
            }
            
            node.addChild(factor);
            result = true;
        }
        
        return result;
    }
}
