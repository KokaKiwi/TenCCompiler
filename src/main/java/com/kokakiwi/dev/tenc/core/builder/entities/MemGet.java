package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class MemGet extends Factor
{
    public final static String TYPE = "MemGet";
    
    public MemGet()
    {
        super(TYPE);
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        List<AssemblyLine> lines = Lists.newLinkedList();
        
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
            lines.add(new Comment("Start memget"));
        }
        Expression address = (Expression) children.get(0);
        
        // TODO Save registers to RAM
        
        Context addressContext = new Context(context);
        addressContext.setValue("__result", "J");
        lines.addAll(address.generate(addressContext));
        
        lines.add(new Instruction(new Opcode(op)).first(
                new RegisterAccess(reg)).second(Datas.data("[J]")));
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        MemGet memGet = new MemGet();
        if (reader.accept(Token.OPEN_SQUARE))
        {
            if (Expression.accept(memGet, reader)
                    && reader.accept(Token.CLOSE_SQUARE))
            {
                node.addChild(memGet);
                
                result = true;
            }
            else
            {
                reader.error("MemGet expected!");
            }
        }
        
        return result;
    }
    
}
