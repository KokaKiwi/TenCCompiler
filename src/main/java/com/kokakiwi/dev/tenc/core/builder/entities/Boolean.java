package com.kokakiwi.dev.tenc.core.builder.entities;

import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Boolean extends Number
{
    public final static String TYPE = "Boolean";
    
    private final boolean      value;
    
    public Boolean(boolean value)
    {
        super(TYPE, value ? 1 : 0);
        this.value = value;
    }
    
    public boolean isValue()
    {
        return value;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.accept(Token.DATA_BOOLEAN))
        {
            Token boolToken = reader.lookahead(-1);
            boolean value = java.lang.Boolean.parseBoolean(boolToken
                    .getMatched());
            
            Boolean bool = new Boolean(value);
            
            node.addChild(bool);
            
            result = true;
        }
        
        return result;
    }
}
