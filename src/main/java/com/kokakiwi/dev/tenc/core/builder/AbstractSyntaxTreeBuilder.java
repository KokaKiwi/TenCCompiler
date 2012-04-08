package com.kokakiwi.dev.tenc.core.builder;

import com.kokakiwi.dev.tenc.core.builder.entities.Program;
import com.kokakiwi.dev.tenc.core.parser.TokenParser;

public class AbstractSyntaxTreeBuilder
{
    private final TokenReader reader;
    
    public AbstractSyntaxTreeBuilder(TokenParser parser)
    {
        reader = new TokenReader(parser.getTokens());
    }
    
    public Program build()
    {
        final Program program = new Program();
        
        program.build(reader);
        
        return program;
    }
}
