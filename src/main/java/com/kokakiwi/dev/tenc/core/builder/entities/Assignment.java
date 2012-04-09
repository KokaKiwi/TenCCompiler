package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
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
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        String value = getValue(context, lines);
        
        int offset = context.getOffset(ident);
        lines.add("SET I, SP");
        if (offset != 0)
        {
            lines.add("ADD I, " + offset);
        }
        
        generateOperationLines(lines, op, value, "[I]");
        
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
            
            for (AbstractSyntaxNode child : children)
            {
                lines.addAll(child.generate(exprContext));
            }
            
            return regToUse;
        }
    }
    
    protected void generateOperationLines(List<String> lines, int op,
            String value, String dest)
    {
        switch (op)
        {
            case Token.ASSIGN:
                lines.add("SET " + dest + ", " + value);
                break;
            
            case Token.PLUSASSIGN:
            case Token.PLUSPLUS:
                lines.add("ADD " + dest + ", " + value);
                break;
            
            case Token.MINUSASSIGN:
            case Token.MINUSMINUS:
                lines.add("SUB " + dest + ", " + value);
                break;
            
            case Token.TIMESASSIGN:
                lines.add("MUL " + dest + ", " + value);
                break;
            
            case Token.DIVIDEASSIGN:
                lines.add("DIV " + dest + ", " + value);
                break;
            
            case Token.MODASSIGN:
                lines.add("MOD " + dest + ", " + value);
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
