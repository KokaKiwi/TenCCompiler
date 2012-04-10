package com.kokakiwi.dev.tenc.core.generator.entities;

public class Datas
{
    public static Data data(String value)
    {
        Data data = null;
        
        if (value.length() > 0)
        {
            if (value.charAt(0) == '[')
            {
                String content = value.substring(1, value.length() - 1);
                try
                {
                    int number = Integer.parseInt(content);
                    data = new Pointer(new Value(number));
                }
                catch (NumberFormatException e)
                {
                    data = new Pointer(new RegisterAccess(content));
                }
            }
            else
            {
                try
                {
                    int number = Integer.parseInt(value);
                    data = new Value(number);
                }
                catch (NumberFormatException e)
                {
                    data = new RegisterAccess(value);
                }
            }
        }
        
        return data;
    }
    
    public static Pointer point(String value)
    {
        Pointer pointer = new Pointer(data(value));
        
        return pointer;
    }
}
