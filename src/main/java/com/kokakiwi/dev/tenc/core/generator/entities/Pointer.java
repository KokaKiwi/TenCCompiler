package com.kokakiwi.dev.tenc.core.generator.entities;

public class Pointer implements Data
{
    private Data data;
    
    public Pointer(Data data)
    {
        if (data instanceof Pointer)
        {
            throw new RuntimeException("Data cannot be a Pointer");
        }
        
        this.data = data;
    }
    
    public Data getData()
    {
        return data;
    }
    
    public void setData(Data data)
    {
        this.data = data;
    }
    
    public String generate()
    {
        String str = "[" + data.generate() + "]";
        
        return str;
    }
}
