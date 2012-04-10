package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.builder.TokenReader;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.entities.*;
import com.kokakiwi.dev.tenc.core.parser.Token;

public class ConditionTerm extends AbstractSyntaxNode
{
    public final static String TYPE = "ConditionTerm";
    
    public ConditionTerm()
    {
        super(TYPE);
    }
    
    @Override
    public List<AssemblyLine> generate(Context context)
    {
        final List<AssemblyLine> lines = Lists.newLinkedList();
        
        if (Compiler.debug)
        {
            lines.add(new Comment("Start condition term"));
        }
        if (children.size() == 1)
        {
            context.setValue("condFactorInstruction",
                    context.getValue("condTermInstruction"));
            AbstractSyntaxNode factor = children.get(0);
            lines.addAll(factor.generate(context));
        }
        else
        {
            boolean first = true;
            String falseLabel = context.getUniqueId("and-chain-false");
            for (int i = 0; i < children.size(); i++)
            {
                AbstractSyntaxNode child = children.get(i);
                if (child instanceof Operation)
                {
                    Operation op = (Operation) child;
                    
                    if (i == 0 || i == children.size() - 1)
                    {
                        Context.error("Unexpected &&");
                    }
                    if (op.getOpType() != Token.AND)
                    {
                        Context.error("Unexpected op " + op.getOpType());
                    }
                    
                    if (first)
                    {
                        String aTrueLabel = context.getUniqueId("a-true");
                        String bTrueLabel = context.getUniqueId("b-true");
                        AbstractSyntaxNode a = children.get(i - 1);
                        AbstractSyntaxNode b = children.get(i + 1);
                        
                        context.setValue("condFactorInstruction",
                                new Instruction(Opcode.SET, new RegisterAccess(
                                        "PC"), new LabelCall(aTrueLabel)));
                        
                        lines.addAll(a.generate(context));
                        lines.add(new Instruction(Opcode.SET,
                                new RegisterAccess("PC"), new LabelCall(
                                        falseLabel)));
                        lines.add(new Label(aTrueLabel));
                        context.setValue("condFactorInstruction",
                                new Instruction(Opcode.SET, new RegisterAccess(
                                        "PC"), new LabelCall(bTrueLabel)));
                        lines.addAll(b.generate(context));
                        lines.add(new Instruction(Opcode.SET,
                                new RegisterAccess("PC"), new LabelCall(
                                        falseLabel)));
                        lines.add(new Label(bTrueLabel));
                        first = false;
                    }
                    else
                    {
                        String cTrueLabel = context.getUniqueId("c-true");
                        AbstractSyntaxNode c = children.get(i + 1);
                        
                        context.setValue("condFactorInstruction",
                                new Instruction(Opcode.SET, new RegisterAccess(
                                        "PC"), new LabelCall(cTrueLabel)));
                        lines.addAll(c.generate(context));
                        lines.add(new Instruction(Opcode.SET,
                                new RegisterAccess("PC"), new LabelCall(
                                        falseLabel)));
                        lines.add(new Label(cTrueLabel));
                    }
                    lines.add((AssemblyLine) context
                            .getValue("condTermInstruction"));
                    lines.add(new Label(falseLabel));
                }
            }
        }
        
        return lines;
    }
    
    public static boolean accept(AbstractSyntaxNode node, TokenReader reader)
    {
        boolean result = false;
        
        final ConditionTerm term = new ConditionTerm();
        
        if (ConditionFactor.accept(term, reader))
        {
            while (reader.accept(Token.AND))
            {
                final Token op = reader.lookahead(-1);
                final Operation operation = new Operation(op.code());
                term.addChild(operation);
                ConditionFactor.accept(term, reader);
            }
            node.addChild(term);
            
            result = true;
        }
        
        return result;
    }
    
}
