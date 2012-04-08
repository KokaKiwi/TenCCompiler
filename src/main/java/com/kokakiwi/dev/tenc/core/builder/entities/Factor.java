package com.kokakiwi.dev.tenc.core.builder.entities;

import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Factor extends AbstractSyntaxNode
{
    public final static String regToUse = "Z";
    
    public Factor(String type)
    {
        super(type);
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (Number.accept(node, reader) || FunctionCall.accept(node, reader)
                || Identifier.accept(node, reader))
        {
            result = true;
        }
        else if (reader.accept(Token.OPEN_PAREN))
        {
            Expression.accept(node, reader);
            reader.expect(Token.CLOSE_PAREN, "factor block end");
            
            result = true;
        }
        else if (MemGet.accept(node, reader))
        {
            result = true;
        }
        
        return result;
    }
    
}
