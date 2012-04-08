package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class IfStatement extends AbstractSyntaxNode
{
    public final static String TYPE = "IfStatement";
    
    public IfStatement()
    {
        super(TYPE);
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        String blockStart = context.getUniqueId("start_if_block");
        String blockEnd = context.getUniqueId("end_if_block");
        
        Condition cond = (Condition) children.get(0);
        Block block = (Block) children.get(1);
        
        context.setValue("condInstruction", "SET PC, " + blockStart);
        lines.addAll(cond.generate(context));
        lines.add("SET PC, " + blockEnd);
        lines.add(":" + blockStart);
        lines.addAll(block.generate(context));
        lines.add(":" + blockEnd);
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        if (reader.accept(Token.IF))
        {
            final IfStatement ifStatement = new IfStatement();
            
            reader.expect(Token.OPEN_PAREN, "if condition start");
            Condition.accept(ifStatement, reader);
            reader.expect(Token.CLOSE_PAREN, "if condition end");
            Block.accept(ifStatement, reader);
            
            node.addChild(ifStatement);
            
            result = true;
        }
        
        return result;
    }
}
