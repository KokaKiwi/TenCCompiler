package com.kokakiwi.dev.tenc.core.generator.entities;

public class LabelCall implements Data
{
    private String name;
    
    public LabelCall(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String generate()
    {
        return name;
    }
    
}
