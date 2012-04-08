package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class Block extends AbstractSyntaxNode
{
    public final static String TYPE = "Block";
    
    public Block()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        for (AbstractSyntaxNode child : children)
        {
            lines.addAll(child.generate(context));
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.accept(Token.OPEN_BRACK))
        {
            final Block block = new Block();
            
            while (Statement.accept(block, reader)
                    || IfStatement.accept(block, reader)
                    || WhileLoop.accept(block, reader))
            {
                
            }
            
            reader.expect(Token.CLOSE_BRACK, "block end");
            
            node.addChild(block);
            
            result = true;
        }
        
        return result;
    }
}
