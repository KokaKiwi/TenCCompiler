package com.kokakiwi.dev.tenc;

import java.io.File;

import com.kokakiwi.dev.tenc.core.Compiler;
import com.kokakiwi.dev.tenc.utils.Version;

public class TenCCompiler
{
    public final static Version VERSION    = Version.parseString("0.1.0");
    
    private String              inputFile  = null;
    private String              outputFile = null;
    
    public TenCCompiler(String[] args)
    {
        if (args.length > 0)
        {
            int pointer = 0;
            while (pointer < args.length)
            {
                final String arg = args[pointer];
                
                if (arg.equalsIgnoreCase("--in"))
                {
                    pointer++;
                    inputFile = args[pointer];
                }
                else if (arg.equalsIgnoreCase("--out"))
                {
                    pointer++;
                    outputFile = args[pointer];
                }
                else if(arg.equalsIgnoreCase("--debug"))
                {
                    Compiler.debug = true;
                }
                
                pointer++;
            }
            
            run();
        }
        else
        {
            printHelp();
        }
    }
    
    private void printHelp()
    {
        System.out.println("DCPU Emulator v" + VERSION.toString());
        System.out
                .println("Usage: java -jar brainfuck-compiler.jar --in [INPUT FILE] --out [OUTPUT FILE]");
    }
    
    private void run()
    {
        final File inputFile = new File(this.inputFile);
        final File outputFile = new File(this.outputFile);
        
        final Compiler compiler = new Compiler(inputFile, outputFile);
        compiler.run();
    }
    
    public static void main(String[] args)
    {
        // new BrainfuckLangageCompiler(args);
        new TenCCompiler(new String[] { "--", "--in", "test.c", "--out", "test.asm" });
    }
}
