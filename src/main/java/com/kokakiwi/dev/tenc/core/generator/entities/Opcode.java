package com.kokakiwi.dev.tenc.core.generator.entities;

public class Opcode
{
    public final static Opcode SET = new Opcode("SET");
    public final static Opcode ADD = new Opcode("ADD");
    public final static Opcode SUB = new Opcode("SUB");
    public final static Opcode MUL = new Opcode("MUL");
    public final static Opcode DIV = new Opcode("DIV");
    public final static Opcode MOD = new Opcode("MOD");
    public final static Opcode SHL = new Opcode("SHL");
    public final static Opcode SHR = new Opcode("SHR");
    public final static Opcode AND = new Opcode("AND");
    public final static Opcode BOR = new Opcode("BOR");
    public final static Opcode XOR = new Opcode("XOR");
    public final static Opcode IFE = new Opcode("IFE");
    public final static Opcode IFN = new Opcode("IFN");
    public final static Opcode IFG = new Opcode("IFG");
    public final static Opcode IFB = new Opcode("IFB");
    public final static Opcode JSR = new Opcode("JSR");
    
    private final String       name;
    
    public Opcode(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
}