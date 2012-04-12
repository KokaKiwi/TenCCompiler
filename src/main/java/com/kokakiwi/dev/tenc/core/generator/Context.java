package com.kokakiwi.dev.tenc.core.generator;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kokakiwi.dev.tenc.core.builder.entities.Function;
import com.kokakiwi.dev.tenc.core.generator.entities.Data;

public class Context
{
    private final String                name;
    private final Map<String, Function> functions = Maps.newLinkedHashMap();
    private final Map<String, Object>   values    = Maps.newLinkedHashMap();
    private final Map<String, Data>     addresses = Maps.newLinkedHashMap();
    private final Map<String, Integer>  ids       = Maps.newLinkedHashMap();
    
    public Context(String name)
    {
        this(name, null, null, null, null);
    }
    
    public Context(Context parent)
    {
        this(parent.getName(), parent);
    }
    
    public Context(String name, Context parent)
    {
        this(name, parent.getAddresses(), parent.getValues(), parent
                .getFunctions(), parent.getIds());
    }
    
    public Context(String name, Map<String, Data> addresses,
            Map<String, Object> values, Map<String, Function> functions,
            Map<String, Integer> ids)
    {
        this.name = name;
        if (addresses != null)
        {
            this.addresses.putAll(addresses);
        }
        
        if (values != null)
        {
            this.values.putAll(values);
        }
        
        if (functions != null)
        {
            this.functions.putAll(functions);
        }
        
        if (ids != null)
        {
            this.ids.putAll(ids);
        }
    }
    
    public String getName()
    {
        return name;
    }
    
    public Map<String, Data> getAddresses()
    {
        return addresses;
    }
    
    public Map<String, Object> getValues()
    {
        return values;
    }
    
    public Map<String, Function> getFunctions()
    {
        return functions;
    }
    
    public Map<String, Integer> getIds()
    {
        return ids;
    }
    
    public Data getAddress(String name)
    {
        return addresses.get(name);
    }
    
    public void setAddress(String name, Data value)
    {
        addresses.put(name, value);
    }
    
    public Object getValue(String name)
    {
        return values.get(name);
    }
    
    public void setValue(String name, Object value)
    {
        values.put(name, value);
    }
    
    public Function getFunction(String name)
    {
        return functions.get(name);
    }
    
    public void setFunction(String name, Function value)
    {
        functions.put(name, value);
    }
    
    public String getUniqueId(String prefix)
    {
        if (!ids.containsKey(prefix))
        {
            ids.put(prefix, 1);
        }
        
        int uuid = ids.get(prefix);
        
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append("_gen_label_");
        sb.append(uuid);
        
        ids.put(prefix, uuid + 1);
        return sb.toString();
    }
    
    public static void error(String s)
    {
        System.err.println(s);
        System.exit(1);
    }
    
    public Context clone()
    {
        Context clone = new Context(name);
        
        clone.getAddresses().putAll(addresses);
        clone.getFunctions().putAll(functions);
        clone.getValues().putAll(values);
        clone.getIds().putAll(ids);
        
        return clone;
    }
}
