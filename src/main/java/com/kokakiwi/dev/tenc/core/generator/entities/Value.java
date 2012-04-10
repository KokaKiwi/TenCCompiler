package com.kokakiwi.dev.tenc.core.generator.entities;

public class Value implements Data
{
    private int value;
    
    public Value(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
    
    public String generate()
    {
        return String.valueOf(value);
    }
}
