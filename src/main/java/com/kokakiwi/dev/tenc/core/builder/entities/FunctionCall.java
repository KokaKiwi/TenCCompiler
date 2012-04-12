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
        
        if (function.getArgs().length != children.size())
        {
            Context.error("Tried to call function with not enought args "
                    + name);
        }
        
        Context callContext = new Context(context);
        int offset = 0;
        
        Function parentFunction = context.getFunction(context.getName());
        for (int i = 0; i < parentFunction.getArgs().length; i++)
        {
            String reg = null;
            switch (i)
            {
                case 0:
                    reg = "A";
                    break;
                
                case 1:
                    reg = "B";
                    break;
                
                case 2:
                    reg = "C";
                    
                    break;
            }
            
            if (reg != null)
            {
                lines.add(new Instruction(Opcode.SET).first(
                        new RegisterAccess("PUSH")).second(
                        new RegisterAccess(reg)));
            }
        }
        
        // TODO Save registers
        
        for (int i = 0; i < children.size(); i++)
        {
            String reg = regToUse;
            switch (i)
            {
                case 0:
                    reg = "A";
                    break;
                
                case 1:
                    reg = "B";
                    break;
                
                case 2:
                    reg = "C";
                    break;
                
                default:
                    lines.add(new Instruction(Opcode.SUB).first(
                            new RegisterAccess("SP")).second(new Value(1)));
                    reg = "[SP]";
                    offset++;
                    break;
            }
            
            callContext.setValue("__result", reg);
            lines.addAll(children.get(i).generate(callContext));
        }
        if (offset > 0)
        {
            lines.add(new Instruction(Opcode.ADD).first(
                    new RegisterAccess("SP")).second(new Value(offset)));
        }
        lines.add(new Instruction(Opcode.JSR).first(new LabelCall(String
                .format(Function.FUNCTION_MANGLE, name))));
        
        if (!function.getReturnType().getType().equalsIgnoreCase("void"))
        {
            String reg = regToUse;
            if (context.getValue("__result") != null)
            {
                reg = (String) context.getValue("__result");
            }
            
            if (!reg.equalsIgnoreCase("A"))
            {
                String op = "SET";
                if (context.getValue("__op") != null)
                {
                    op = (String) context.getValue("__op");
                }
                
                lines.add(new Instruction(new Opcode(op)).first(
                        new RegisterAccess(reg))
                        .second(new RegisterAccess("A")));
            }
        }
        
        for (int i = 0; i < parentFunction.getArgs().length; i++)
        {
            String reg = null;
            switch (i)
            {
                case 0:
                    reg = "A";
                    break;
                
                case 1:
                    reg = "B";
                    break;
                
                case 2:
                    reg = "C";
                    
                    break;
            }
            
            if (reg != null)
            {
                lines.add(new Instruction(Opcode.SET).first(
                        new RegisterAccess(reg)).second(
                        new RegisterAccess("POP")));
            }
        }
        
        // TODO Retrieve registers
        
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
