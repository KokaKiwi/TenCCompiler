package com.kokakiwi.dev.tenc.core.generator.entities;

public class Comment implements AssemblyLine
{
    private String message;
    
    public Comment(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public String generate()
    {
        String str = ";" + message;
        
        return str;
    }
    
}
