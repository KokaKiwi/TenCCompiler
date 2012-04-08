package com.kokakiwi.dev.tenc.core.generator;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kokakiwi.dev.tenc.core.builder.entities.Function;
import com.kokakiwi.dev.tenc.core.builder.entities.Program;

public class Generator
{
    private final Program               program;
    private final Map<String, Function> functions = Maps.newLinkedHashMap();
    
    public Generator(Program program)
    {
        this.program = program;
    }
    
    public List<String> generate()
    {
        // Add base functions
        
        final List<String> assembly = Lists.newLinkedList();
        // From TenC original project
        assembly.add("SET PUSH, exit");
        assembly.add("SET PUSH, exit");
        assembly.add("SET PC, func_main");
        assembly.add(":exit SUB PC, 1");
        
        Context context = new Context("root");
        assembly.addAll(program.generate(context));
        
        return assembly;
    }
    
    public Program getProgram()
    {
        return program;
    }
    
    public Map<String, Function> getFunctions()
    {
        return functions;
    }
}
