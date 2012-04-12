package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Assignment extends AbstractSyntaxNode
{
    public final static String TYPE     = "Assignment";
    public final static String regToUse = "X";
    
    private final String       ident;
    protected int              op;
    
    public Assignment()
    {
        this(null);
    }
    
    public Assignment(String ident)
    {
        this(ident, -1);
    }
    
    public Assignment(String type, String ident)
    {
        this(type, ident, -1);
    }
    
    public Assignment(String ident, int op)
    {
        this(TYPE, ident, op);
    }
    
    public Assignment(String type, String ident, int op)
    {
        super(type);
        this.ident = ident;
        this.op = op;
    }
    
    public String getIdent()
    {
        return ident;
    }
    
    public int getOp()
    {
        return op;
    }
    
    public void setOp(int op)
    {
        this.op = op;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(ident);
        
        return sb.toString();
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        Data value = getValue(context, lines);
        
        if (context.getAddress(ident) == null)
        {
            Context.error("Try to assign a non-existent variable: " + ident);
        }
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start assignment"));
        }
        
        Data data = context.getAddress(ident);
        
        if (data instanceof RegisterAccess)
        {
            generateOperationLines(lines, op, data, value);
        }
        else if (data instanceof Pointer)
        {
            Data pointed = ((Pointer) data).getData();
            if (pointed instanceof Value)
            {
                generateOperationLines(lines, op, data, value);
            }
            else if (pointed instanceof RegisterAccess)
            {
                RegisterAccess access = (RegisterAccess) pointed;
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
                    
                    generateOperationLines(lines, op,
                            Datas.data("[I" + opz + access.getOffset() + "]"),
                            value);
                }
                else
                {
                    generateOperationLines(lines, op, data, value);
                }
            }
        }
        
        return lines;
    }
    
    protected Data getValue(Context context, List<AssemblyLine> lines)
    {
        if (op == Token.PLUSPLUS || op == Token.MINUSMINUS)
        {
            return new Value(1);
        }
        else
        {
            Expression expr = (Expression) children.get(0);
            
            Context exprContext = new Context(context);
            exprContext.setValue("__result", regToUse);
            
            lines.addAll(expr.generate(exprContext));
            
            return Datas.data(regToUse);
        }
    }
    
    protected void generateOperationLines(List<AssemblyLine> lines, int op,
            Data dest, Data value)
    {
        switch (op)
        {
            case Token.ASSIGN:
                lines.add(new Instruction(Opcode.SET, dest, value));
                break;
            
            case Token.PLUSASSIGN:
            case Token.PLUSPLUS:
                lines.add(new Instruction(Opcode.ADD, dest, value));
                break;
            
            case Token.MINUSASSIGN:
            case Token.MINUSMINUS:
                lines.add(new Instruction(Opcode.SUB, dest, value));
                break;
            
            case Token.TIMESASSIGN:
                lines.add(new Instruction(Opcode.MUL, dest, value));
                break;
            
            case Token.DIVIDEASSIGN:
                lines.add(new Instruction(Opcode.DIV, dest, value));
                break;
            
            case Token.MODASSIGN:
                lines.add(new Instruction(Opcode.MOD, dest, value));
                break;
        }
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.lookahead(0).code() == Token.IDENTIFIER
                && (reader.lookahead(1).code() == Token.ASSIGN
                        || reader.lookahead(1).code() == Token.PLUSASSIGN
                        || reader.lookahead(1).code() == Token.MINUSASSIGN
                        || reader.lookahead(1).code() == Token.TIMESASSIGN || reader
                        .lookahead(1).code() == Token.DIVIDEASSIGN))
        {
            reader.accept(Token.IDENTIFIER);
            reader.accept();
            
            final Token assigned = reader.lookahead(-2);
            final Token opToken = reader.lookahead(-1);
            
            final Assignment assignment = new Assignment(assigned.getMatched(),
                    opToken.code());
            
            Expression.accept(assignment, reader);
            
            node.addChild(assignment);
            
            result = true;
        }
        else if (reader.lookahead(0).code() == Token.IDENTIFIER
                && (reader.lookahead(1).code() == Token.PLUSPLUS || reader
                        .lookahead(1).code() == Token.MINUSMINUS))
        {
            reader.accept(Token.IDENTIFIER);
            reader.accept();
            
            final Token assigned = reader.lookahead(-2);
            final Token opToken = reader.lookahead(-1);
            
            final Assignment assignment = new Assignment(assigned.getMatched(),
                    opToken.code());
            
            node.addChild(assignment);
            
            result = true;
        }
        
        if (MemSet.accept(node, reader))
        {
            result = true;
        }
        
        return result;
    }
}
