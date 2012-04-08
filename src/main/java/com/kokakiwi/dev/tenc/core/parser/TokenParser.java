package com.kokakiwi.dev.tenc.core.parser;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class TokenParser implements TokenParserInterface
{
    private final List<Token> tokens = Lists.newLinkedList();
    
    public void addToken(int code)
    {
        addToken(yytext(), code);
    }
    
    public void addToken(String matched, int code)
    {
        final Token token = new Token(matched, code);
        addToken(token);
    }
    
    public void addToken(Token token)
    {
        tokens.add(token);
    }
    
    public List<Token> getTokens()
    {
        return tokens;
    }
}
