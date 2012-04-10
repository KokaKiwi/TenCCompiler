package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Number extends Factor
{
    public final static String TYPE = "Number";
    
    private final int          number;
    
    public Number(int number)
    {
        this(TYPE, number);
    }
    
    public Number(String type, int number)
    {
        super(type);
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
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        String reg = regToUse;
        if (context.getValue("__result") != null)
        {
            reg = (String) context.getValue("__result");
        }
        
        String op = "SET";
        if (context.getValue("__op") != null)
        {
            op = (String) context.getValue("__op");
        }
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start number"));
        }
        lines.add(new Instruction(new Opcode(op), new RegisterAccess(reg),
                new Value(number)));
        
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
        else if (Boolean.accept(node, reader))
        {
            result = true;
        }
        
        return result;
    }
}
