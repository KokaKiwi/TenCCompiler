package com.kokakiwi.dev.tenc.core.generator.entities;

public class RegisterAccess implements Data
{
    private String registerName;
    
    public RegisterAccess(String registerName)
    {
        this.registerName = registerName;
    }
    
    public String getRegisterName()
    {
        return registerName;
    }
    
    public void setRegisterName(String registerName)
    {
        this.registerName = registerName;
    }
    
    public String generate()
    {
        return registerName;
    }
}
