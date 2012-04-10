package com.kokakiwi.dev.tenc.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Lists;
import com.kokakiwi.dev.tenc.core.builder.AbstractSyntaxTreeBuilder;
import com.kokakiwi.dev.tenc.core.builder.entities.Program;
import com.kokakiwi.dev.tenc.core.generator.Generator;
import com.kokakiwi.dev.tenc.core.generator.entities.AssemblyLine;
import com.kokakiwi.dev.tenc.core.generator.entities.Comment;
import com.kokakiwi.dev.tenc.core.parser.TencTokenMaker;
import com.kokakiwi.dev.tenc.core.parser.TokenParser;

public class Compiler implements Runnable
{
    public static boolean             debug    = false;
    public static boolean             comments = true;
    
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
            final List<AssemblyLine> assembly = generator.generate();
            
            List<String> lines = Lists.newLinkedList();
            for (AssemblyLine line : assembly)
            {
                if (line instanceof Comment && comments)
                {
                    lines.add(line.generate());
                }
                else
                {
                    lines.add(line.generate());
                }
            }
            
            IOUtils.writeLines(lines, null, new FileOutputStream(outputFile));
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
