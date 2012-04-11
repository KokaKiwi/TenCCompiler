package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class IfStatement extends AbstractSyntaxNode
{
    public final static String TYPE = "IfStatement";
    
    public IfStatement()
    {
        super(TYPE);
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start if"));
        }
        String blockStart = context.getUniqueId("start_if_block");
        String blockEnd = context.getUniqueId("end_if_block");
        String elseBlockStart = null;
        if (children.size() > 2)
        {
            elseBlockStart = context.getUniqueId("start_else_block");
        }
        
        Condition cond = (Condition) children.get(0);
        Block block = (Block) children.get(1);
        
        context.setValue("condInstruction",
                new Instruction(Opcode.SET).first(new RegisterAccess("PC"))
                        .second(new LabelCall(blockStart)));
        lines.addAll(cond.generate(context));
        if (children.size() > 2)
        {
            lines.add(new Instruction(Opcode.SET, new RegisterAccess("PC"),
                    new LabelCall(elseBlockStart)));
        }
        else
        {
            lines.add(new Instruction(Opcode.SET, new RegisterAccess("PC"),
                    new LabelCall(blockEnd)));
        }
        lines.add(new Label(blockStart));
        lines.addAll(block.generate(context));
        if (children.size() > 2)
        {
            AbstractSyntaxNode elseBlock = children.get(2);
            
            lines.add(new Instruction(Opcode.SET).first(
                    new RegisterAccess("PC")).second(new LabelCall(blockEnd)));
            lines.add(new Label(elseBlockStart));
            lines.addAll(elseBlock.generate(context));
            
        }
        lines.add(new Label(blockEnd));
        
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
            
            if (reader.accept(Token.ELSE))
            {
                if (!IfStatement.accept(ifStatement, reader)
                        && !Block.accept(ifStatement, reader))
                {
                    Context.error("Else block expected");
                }
            }
            
            node.addChild(ifStatement);
            
            result = true;
        }
        
        return result;
    }
}
