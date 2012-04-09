package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
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
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        int offset = context.getOffset(name);
        lines.add("SET I, SP");
        if (offset != 0)
        {
            lines.add("ADD I, " + offset);
        }
        lines.add("SET " + regToUse + ", [I]");
        
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
