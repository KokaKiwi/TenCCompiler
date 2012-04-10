package com.kokakiwi.dev.tenc.core.generator;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kokakiwi.dev.tenc.core.builder.entities.Function;
import com.kokakiwi.dev.tenc.core.builder.entities.Program;
import com.kokakiwi.dev.tenc.core.generator.entities.*;

public class Generator
{
    private final Program               program;
    private final Map<String, Function> functions = Maps.newLinkedHashMap();
    
    public Generator(Program program)
    {
        this.program = program;
    }
    
    public List<AssemblyLine> generate()
    {
        // Add base functions
        
        List<AssemblyLine> assembly = Lists.newLinkedList();
        // From TenC original project
        assembly.add(new Instruction(Opcode.SET).first(
                new RegisterAccess("PUSH")).second(new LabelCall("exit")));
        assembly.add(new Instruction(Opcode.SET).first(
                new RegisterAccess("PUSH")).second(new LabelCall("exit")));
        assembly.add(new Instruction(Opcode.SET)
                .first(new RegisterAccess("PC")).second(
                        new LabelCall("func_main")));
        assembly.add(new Label("exit"));
        assembly.add(new Instruction(Opcode.SUB)
                .first(new RegisterAccess("PC")).second(new Value(1)));
        
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
