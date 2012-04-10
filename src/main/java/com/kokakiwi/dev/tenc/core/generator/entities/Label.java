package com.kokakiwi.dev.tenc.core.generator.entities;

public class Label implements AssemblyLine
{
    private String name;
    
    public Label(String name)
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
        String str = ":" + name;
        
        return str;
    }
    
}
