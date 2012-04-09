package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class MemSet extends Assignment
{
    public final static String TYPE     = "MemSet";
    public final static String regToUse = "X";
    
    public MemSet()
    {
        super(TYPE, null);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        List<String> lines = Lists.newLinkedList();
        
        String value = getValue(context, lines);
        
        Expression address = (Expression) children.get(0);
        context.setValue("__result", "J");
        lines.addAll(address.generate(context));
        
        generateOperationLines(lines, op, value, "[J]");
        
        return lines;
    }
    
    protected String getValue(Context context, List<String> lines)
    {
        if (op == Token.PLUSPLUS || op == Token.MINUSMINUS)
        {
            return "1";
        }
        else
        {
            Context exprContext = new Context(context);
            exprContext.setValue("__result", regToUse);
            
            Expression value = (Expression) children.get(1);
            context.setValue("__result", regToUse);
            lines.addAll(value.generate(context));
            
            return regToUse;
        }
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        MemSet memSet = new MemSet();
        if (reader.accept(Token.OPEN_SQUARE))
        {
            if (Expression.accept(memSet, reader)
                    && reader.accept(Token.CLOSE_SQUARE))
            {
                if (reader.lookahead(0).code() == Token.ASSIGN
                        || reader.lookahead(0).code() == Token.PLUSASSIGN
                        || reader.lookahead(0).code() == Token.MINUSASSIGN
                        || reader.lookahead(0).code() == Token.TIMESASSIGN
                        || reader.lookahead(0).code() == Token.DIVIDEASSIGN)
                {
                    memSet.setOp(reader.lookahead(0).code());
                    reader.accept();
                    
                    Expression.accept(memSet, reader);
                    
                    node.addChild(memSet);
                    
                    result = true;
                }
                else if (reader.lookahead(0).code() == Token.PLUSPLUS
                        || reader.lookahead(0).code() == Token.MINUSMINUS)
                {
                    memSet.setOp(reader.lookahead(0).code());
                    reader.accept();
                    
                    node.addChild(memSet);
                    
                    result = true;
                }
                else
                {
                    reader.error("MemSet Assign sign expected!");
                }
            }
            else
            {
                reader.error("MemSet expected!");
            }
        }
        
        return result;
    }
}
