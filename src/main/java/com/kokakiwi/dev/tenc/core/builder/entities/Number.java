package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Number extends Factor
{
    public final static String TYPE = "Number";
    
    private final int          number;
    
    public Number(int number)
    {
        super(TYPE);
        this.number = number;
    }
    
    public int number()
    {
        return number;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(number);
        
        return sb.toString();
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        lines.add("SET " + regToUse + ", " + number);
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.accept(Token.DATA_INTEGER))
        {
            final Token numberToken = reader.lookahead(-1);
            final int number = Integer.parseInt(numberToken.getMatched());
            
            final Number number2 = new Number(number);
            node.addChild(number2);
            
            result = true;
        }
        else if (reader.accept(Token.DATA_HEX))
        {
            final Token hexToken = reader.lookahead(-1);
            final String hexString = hexToken.getMatched();
            int number;
            if (hexString.endsWith("0x"))
            {
                number = new Integer(0);
            }
            else
            {
                number = Integer.parseInt(hexString.split("0x")[1], 16);
            }
            
            final Number number2 = new Number(number);
            node.addChild(number2);
            
            result = true;
        }
        else if (reader.accept(Token.DATA_CHAR))
        {
            final Token charToken = reader.lookahead(-1);
            final int number = charToken.getMatched().charAt(1);
            
            final Number number2 = new Number(number);
            node.addChild(number2);
            
            result = true;
        }
        
        return result;
    }
}
