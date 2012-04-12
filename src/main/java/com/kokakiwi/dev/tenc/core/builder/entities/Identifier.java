package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Identifier extends Factor
{
    public final static String TYPE = "Identifier";
    protected final String     name;
    
    public Identifier(String name)
    {
        this(TYPE, name);
    }
    
    public Identifier(String type, String name)
    {
        super(type);
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(name);
        
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
            lines.add(new Comment("Start identifier"));
        }
        Data data = context.getAddress(name);
        
        if (data instanceof Value)
        {
            lines.add(new Instruction(new Opcode(op)).first(
                    new RegisterAccess(reg)).second(data));
        }
        else if (data instanceof RegisterAccess)
        {
            lines.add(new Instruction(new Opcode(op)).first(
                    new RegisterAccess(reg)).second(data));
        }
        else if (data instanceof Pointer)
        {
            Data value = ((Pointer) data).getData();
            if (value instanceof Value)
            {
                lines.add(new Instruction(new Opcode(op)).first(
                        new RegisterAccess(reg)).second(data));
            }
            else if (value instanceof RegisterAccess)
            {
                RegisterAccess access = (RegisterAccess) value;
                if (access.getRegisterName().equalsIgnoreCase("SP")
                        && access.getOffset() != 0)
                {
                    lines.add(new Instruction(Opcode.SET)
                            .first(Datas.data("I")).second(access));
                    
                    String opz = "+";
                    if (access.getOffset() < 0)
                    {
                        opz = "";
                    }
                    
                    lines.add(new Instruction(new Opcode(op)).first(
                            new RegisterAccess(reg)).second(
                            Datas.data("[I" + opz + access.getOffset() + "]")));
                }
                else
                {
                    lines.add(new Instruction(new Opcode(op)).first(
                            new RegisterAccess(reg)).second(data));
                }
            }
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.accept(Token.IDENTIFIER))
        {
            final Token idToken = reader.lookahead(-1);
            final String name = idToken.getMatched();
            
            final Identifier identifier = new Identifier(name);
            node.addChild(identifier);
            
            result = true;
        }
        
        return result;
    }
}
