package com.kokakiwi.dev.tenc.core.generator.entities;

public class Instruction implements AssemblyLine
{
    private Opcode opcode;
    private Data   first;
    private Data   second;
    
    public Instruction(Opcode opcode)
    {
        this(opcode, null);
    }
    
    public Instruction(Opcode opcode, Data first)
    {
        this(opcode, first, null);
    }
    
    public Instruction(Opcode opcode, Data first, Data second)
    {
        this.opcode = opcode;
        this.first = first;
        this.second = second;
    }
    
    public Opcode getOpcode()
    {
        return opcode;
    }
    
    public void setOpcode(Opcode opcode)
    {
        this.opcode = opcode;
    }
    
    public Data getFirst()
    {
        return first;
    }
    
    public void setFirst(Data first)
    {
        this.first = first;
    }
    
    public Instruction first(Data first)
    {
        this.first = first;
        
        return this;
    }
    
    public Data getSecond()
    {
        return second;
    }
    
    public void setSecond(Data second)
    {
        this.second = second;
    }
    
    public Instruction second(Data second)
    {
        this.second = second;
        
        return this;
    }
    
    public String generate()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(opcode.getName());
        
        if (first != null)
        {
            sb.append(' ');
            sb.append(first.generate());
        }
        
        if (second != null)
        {
            sb.append(", ");
            sb.append(second.generate());
        }
        
        return sb.toString();
    }
    
    public String toString()
    {
        return generate();
    }
}
