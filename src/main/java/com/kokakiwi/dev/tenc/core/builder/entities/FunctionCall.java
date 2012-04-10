package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class FunctionCall extends Factor
{
    public final static String TYPE = "FunctionCall";
    
    private final String       name;
    
    public FunctionCall(String name)
    {
        super(TYPE);
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
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start function call"));
        }
        Function function = context.getFunction(name);
        if (function == null)
        {
            Context.error("Tried to call non-existant function " + name);
        }
        
        lines.add(new Instruction(Opcode.SET, new RegisterAccess("PUSH"),
                new Value(0)));
        lines.add(new Instruction(Opcode.SET, new RegisterAccess("I"),
                new RegisterAccess("SP")));
        
        context.setValue("__result", regToUse);
        for (AbstractSyntaxNode child : children)
        {
            lines.addAll(child.generate(context));
            lines.add(new Instruction(Opcode.SET, new RegisterAccess("PUSH"),
                    new RegisterAccess(regToUse)));
        }
        
        lines.add(new Instruction(Opcode.SET, new Pointer(new RegisterAccess(
                "I")), new RegisterAccess("PC")));
        lines.add(new Instruction(Opcode.SET, new Pointer(new RegisterAccess(
                "I")), new Value(3)));
        lines.add(new Instruction(Opcode.SET, new RegisterAccess("PC"),
                new LabelCall(String.format(Function.FUNCTION_MANGLE, name))));
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.lookahead(0).code() == Token.IDENTIFIER
                && reader.lookahead(1).code() == Token.OPEN_PAREN)
        {
            final Token funcName = reader.lookahead(0);
            
            final FunctionCall functionCall = new FunctionCall(
                    funcName.getMatched());
            
            reader.accept(Token.IDENTIFIER);
            reader.accept(Token.OPEN_PAREN);
            
            boolean searchArgs = true;
            while (searchArgs)
            {
                if (!Expression.accept(functionCall, reader)
                        || !reader.accept(Token.COMMA))
                {
                    searchArgs = false;
                }
            }
            reader.expect(Token.CLOSE_PAREN, "function call args close");
            
            node.addChild(functionCall);
            
            result = true;
        }
        
        return result;
    }
}
