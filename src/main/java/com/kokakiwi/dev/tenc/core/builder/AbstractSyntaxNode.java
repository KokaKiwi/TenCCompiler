package com.kokakiwi.dev.tenc.core.builder;

import java.util.List;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.generator.Context;
import com.kokakiwi.dev.tenc.core.generator.Renderable;

public abstract class AbstractSyntaxNode implements Renderable
{
    protected final List<AbstractSyntaxNode> children = Lists.newLinkedList();
    protected final String                   type;
    protected AbstractSyntaxNode             parent   = null;
    
    public AbstractSyntaxNode(String type)
    {
        this.type = type;
    }
    
    public List<AbstractSyntaxNode> getChildren()
    {
        return children;
    }
    
    public void addChild(AbstractSyntaxNode child)
    {
        children.add(child);
        child.setParent(this);
    }
    
    List<AbstractSyntaxNode> getSiblings()
    {
        if (parent == null)
        {
            return null;
        }
        return parent.getChildren();
    }
    
    public List<String> generate(Context context)
    {
        final List<String> lines = Lists.newLinkedList();
        
        for (final AbstractSyntaxNode child : children)
        {
            lines.addAll(child.generate(context));
        }
        
        return lines;
    }
    
    public int distanceFromRoot()
    {
        int dist = 0;
        
        AbstractSyntaxNode n = this;
        while (n.getParent() != null)
        {
            dist++;
            n = n.getParent();
        }
        
        return dist;
    }
    
    public String getType()
    {
        return type;
    }
    
    public AbstractSyntaxNode getParent()
    {
        return parent;
    }
    
    public void setParent(AbstractSyntaxNode parent)
    {
        this.parent = parent;
    }
    
    public String represent()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append(type);
        builder.append(" [");
        builder.append(getSiblings() != null ? getSiblings().size() : -1);
        builder.append("] ");
        return builder.toString();
    }
    
    @Override
    public String toString()
    {
        final int distRoot = distanceFromRoot();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(represent());
        sb.append('\n');
        
        for (final AbstractSyntaxNode child : children)
        {
            for (int i = 0; i <= distRoot; i++)
            {
                sb.append('\t');
            }
            sb.append(child.toString());
        }
        
        return sb.toString();
    }
}
