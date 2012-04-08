package com.kokakiwi.dev.tenc.core.builder.entities;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxNode;
import com.kokakiwi.dev.tenc.core.generator.Context;

public class Operation extends AbstractSyntaxNode
{
    public final static String TYPE = "Operation";
    
    private final int          opType;
    
    public Operation(int opType)
    {
        super(TYPE);
        this.opType = opType;
    }
    
    public int getOpType()
    {
        return opType;
    }
    
    @Override
    public String represent()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(super.represent());
        
        sb.append(" : ");
        sb.append(opType);
        
        return sb.toString();
    }
    
    @Override
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        return lines;
    }
    
}
