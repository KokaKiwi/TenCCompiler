package com.kokakiwi.dev.tenc.core.generator.entities;

public class RegisterAccess implements Data
{
    private String registerName;
    private int    offset;
    
    public RegisterAccess(String registerName)
    {
        this(registerName, 0);
    }
    
    public RegisterAccess(String registerName, int offset)
    {
        this.registerName = registerName;
        this.offset = offset;
    }
    
    public String getRegisterName()
    {
        return registerName;
    }
    
    public void setRegisterName(String registerName)
    {
        this.registerName = registerName;
    }
    
    public int getOffset()
    {
        return offset;
    }
    
    public void setOffset(int offset)
    {
        this.offset = offset;
    }
    
    public String generate()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(registerName);
        
        return sb.toString();
    }
}
