package com.kokakiwi.dev.tenc.core.builder.entities;

import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Statement
{
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (Assignment.accept(node, reader) || Creation.accept(node, reader)
                || Expression.accept(node, reader)
                || Return.accept(node, reader))
        {
            reader.expect(Token.SEMICOLON, "statement end");
            result = true;
        }
        
        return result;
    }
}
