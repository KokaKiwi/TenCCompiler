package com.kokakiwi.dev.tenc.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxTreeBuilder;
import com.kokakiwi.dev.tenc.core.builder.entities.Program;
import com.kokakiwi.dev.tenc.core.generator.Generator;
import com.kokakiwi.dev.tenc.core.parser.TencTokenMaker;
import com.kokakiwi.dev.tenc.core.parser.TokenParser;

public class Compiler implements Runnable
{
    private final File                inputFile;
    private final File                outputFile;
    
    private TokenParser               parser;
    private AbstractSyntaxTreeBuilder syntaxTreeBuilder;
    
    public Compiler(File inputFile, File outputFile)
    {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }
    
    public File getInputFile()
    {
        return inputFile;
    }
    
    public File getOutputFile()
    {
        return outputFile;
    }
    
    public void run()
    {
        if (!inputFile.exists())
        {
            return;
        }
        
        try
        {
            final FileReader reader = new FileReader(inputFile);
            parser = new TencTokenMaker(reader);
            parser.parse();
            
            syntaxTreeBuilder = new AbstractSyntaxTreeBuilder(parser);
            final Program program = syntaxTreeBuilder.build();
            
            System.out.println(program.toString());
            
            final Generator generator = new Generator(program);
            final List<String> assembly = generator.generate();
            
            IOUtils.writeLines(assembly, null, new FileOutputStream(outputFile));
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public TokenParser getParser()
    {
        return parser;
    }
    
    public AbstractSyntaxTreeBuilder getSyntaxTreeBuilder()
    {
        return syntaxTreeBuilder;
    }
}
