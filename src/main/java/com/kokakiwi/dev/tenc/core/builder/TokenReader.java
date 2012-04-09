package com.kokakiwi.dev.tenc.core.builder;

import java.util.List;

import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class TokenReader
{
    private final List<Token> tokens;
    private int               position = 0;
    
    public TokenReader(List<Token> tokens)
    {
        this.tokens = tokens;
    }
    
    public boolean accept(int code)
    {
        return accept(new Token(null, code));
    }
    
    public boolean accept(Token token)
    {
        return accept(token, 0);
    }
    
    public boolean accept(int code, int lookahead)
    {
        return accept(new Token(null, code), lookahead);
    }
    
    public boolean accept(Token token, int lookahead)
    {
        if (lookahead(lookahead).code() == token.code())
        {
            if (Compiler.debug)
            {
                System.out.println("Accepted token: " + lookahead(lookahead));
            }
            return accept();
        }
        return false;
    }
    
    public boolean accept()
    {
        position++;
        return true;
    }
    
    public void expect(int code, String expected)
    {
        expect(new Token(null, code), expected);
    }
    
    public void expect(Token token, String expected)
    {
        expect(accept(token), "Syntax error: found " + lookahead(0)
                + ", expected " + token.toString() + " : " + expected);
    }
    
    public void expect(boolean b, String s)
    {
        if (!b)
        {
            error(s);
        }
    }
    
    public void error(String s)
    {
        System.err.println(s);
        System.exit(1);
    }
    
    public Token lookahead(int i)
    {
        if (position + i >= tokens.size())
        {
            return new Token(null, Token.EOF);
        }
        return tokens.get(position + i);
    }
}
