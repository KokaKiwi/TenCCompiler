package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

/**
 * Children are statements in function.
 */
public class Function extends AbstractSyntaxNode
{
    public final static String TYPE            = "Function";
    public final static String FUNCTION_MANGLE = "func_%s";
    
    protected final Type       returnType;
    protected final Type[]     args;
    protected int              size            = 0;
    
    public Function(Type returnType, Type[] args)
    {
        super(TYPE);
        this.returnType = returnType;
        this.args = args;
    }
    
    public Type getReturnType()
    {
        return returnType;
    }
    
    public Type[] getArgs()
    {
        return args;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(returnType.toString());
        
        for (final Type arg : args)
        {
            sb.append(" , ");
            sb.append(arg.toString());
        }
        
        return sb.toString();
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        lines.add(new Label(
                String.format(FUNCTION_MANGLE, returnType.getName())));
        
        Context funcContext = generateContext(context);
        
        if (size > 0)
        {
            lines.add(new Instruction(Opcode.SUB).first(
                    new RegisterAccess("SP")).second(new Value(size)));
        }
        
        Block block = (Block) children.get(0);
        lines.addAll(block.generate(funcContext));
        
        if (size > 0)
        {
            lines.add(new Instruction(Opcode.ADD).first(
                    new RegisterAccess("SP")).second(new Value(size)));
        }
        if (!returnType.getName().equalsIgnoreCase("main"))
        {
            lines.add(new Instruction(Opcode.SET).first(
                    new RegisterAccess("PC")).second(new RegisterAccess("POP")));
        }
        else
        {
            lines.add(new Instruction(Opcode.SET).first(
                    new RegisterAccess("PC")).second(new LabelCall("exit")));
        }
        
        return lines;
    }
    
    protected Context generateContext(Context parent)
    {
        final Context funcContext = new Context(returnType.getName(), parent);
        
        Block block = (Block) children.get(0);
        for (AbstractSyntaxNode child : block.getChildren())
        {
            if (child instanceof Creation)
            {
                Creation creation = (Creation) child;
                funcContext.setAddress(creation.getDataType().getName(),
                        new Pointer(new RegisterAccess("SP", size)));
                size++;
            }
        }
        
        for (int i = 0; i < args.length; i++)
        {
            Data address = null;
            switch (i)
            {
                case 0:
                    address = new RegisterAccess("A");
                    break;
                case 1:
                    address = new RegisterAccess("B");
                    break;
                case 2:
                    address = new RegisterAccess("C");
                    break;
                
                default:
                    address = new Pointer(new RegisterAccess("SP", size));
                    size++;
                    break;
            }
            funcContext.setAddress(args[i].getName(), address);
        }
        
        return funcContext;
    }
    
    public static boolean accept(Program program, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.lookahead(0).code() == Token.DATA_TYPE_NAME
                && reader.lookahead(1).code() == Token.IDENTIFIER
                && reader.lookahead(2).code() == Token.OPEN_PAREN)
        {
            final Token type = reader.lookahead(0);
            final Token name = reader.lookahead(1);
            
            reader.accept(Token.DATA_TYPE_NAME);
            reader.accept(Token.IDENTIFIER);
            reader.accept(Token.OPEN_PAREN);
            
            final List<Type> types = Lists.newLinkedList();
            boolean searchArgs = true;
            while (searchArgs)
            {
                if (reader.accept(Token.DATA_TYPE_NAME))
                {
                    reader.expect(Token.IDENTIFIER, "argname");
                    
                    final Token argType = reader.lookahead(-2);
                    final Token argName = reader.lookahead(-1);
                    
                    final Type arg = new Type(argType.getMatched(),
                            argName.getMatched());
                    
                    types.add(arg);
                }
                
                if (!reader.accept(Token.COMMA))
                {
                    searchArgs = false;
                }
            }
            reader.expect(Token.CLOSE_PAREN, "function args close");
            
            final Type[] args = types.toArray(new Type[0]);
            
            final Function function = new Function(new Type(type.getMatched(),
                    name.getMatched()), args);
            
            Block.accept(function, reader);
            
            program.addChild(function);
            
            result = true;
        }
        
        return result;
    }
    
    public static class Type
    {
        private final String type;
        private final String name;
        
        public Type(String type, String name)
        {
            this.type = type;
            this.name = name;
        }
        
        public String getType()
        {
            return type;
        }
        
        public String getName()
        {
            return name;
        }
        
        @Override
        public String toString()
        {
            final StringBuilder builder = new StringBuilder();
            builder.append("[");
            if (type != null)
            {
                builder.append("type=");
                builder.append(type);
                builder.append(", ");
            }
            if (name != null)
            {
                builder.append("name=");
                builder.append(name);
            }
            builder.append("]");
            return builder.toString();
        }
    }
}
