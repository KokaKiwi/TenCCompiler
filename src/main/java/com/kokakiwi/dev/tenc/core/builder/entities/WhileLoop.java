package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class WhileLoop extends AbstractSyntaxNode
{
    public final static String TYPE = "WhileLoop";
    
    public WhileLoop()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        String condStart = context.getUniqueId("start_while_cond");
        String blockStart = context.getUniqueId("start_while_block");
        String blockEnd = context.getUniqueId("end_while_block");
        
        Condition cond = (Condition) children.get(0);
        Block block = (Block) children.get(1);
        
        lines.add(":" + condStart);
        context.setValue("condInstruction", "SET PC, " + blockStart);
        lines.addAll(cond.generate(context));
        lines.add("SET PC, " + blockEnd);
        lines.add(":" + blockStart);
        lines.addAll(block.generate(context));
        lines.add("SET PC, " + condStart);
        lines.add(":" + blockEnd);
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.accept(Token.WHILE))
        {
            final WhileLoop whileLoop = new WhileLoop();
            
            reader.expect(Token.OPEN_PAREN, "if condition start");
            Condition.accept(whileLoop, reader);
            reader.expect(Token.CLOSE_PAREN, "if condition end");
            Block.accept(whileLoop, reader);
            
            node.addChild(whileLoop);
            
            result = true;
        }
        
        return result;
    }
}
