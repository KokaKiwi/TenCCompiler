package com.kokakiwi.dev.tenc.core.generator;

import java.util.List;

import com.kokakiwi.dev.tenc.core.generator.entities.AssemblyLine;

public interface Renderable
{
    public List<AssemblyLine> generate(Context context);
}
