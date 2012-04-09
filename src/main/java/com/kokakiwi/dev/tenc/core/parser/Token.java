package com.kokakiwi.dev.tenc.core.parser;

public class Token
{
    /* Values */
    public final static int EOF                = -1;
    
    /* Reserved */
    public final static int IF                 = 0;
    public final static int WHILE              = 1;
    public final static int RETURN             = 2;
    public final static int NULL               = 3;
    public final static int UNKNOWN            = 4;
    
    /* Blocks */
    public final static int OPEN_BRACK         = 30;
    public final static int CLOSE_BRACK        = 31;
    
    public final static int OPEN_PAREN         = 32;
    public final static int CLOSE_PAREN        = 33;
    
    public final static int OPEN_SQUARE        = 34;
    public final static int CLOSE_SQUARE       = 35;
    
    /* Operators */
    public final static int PLUS               = 90;
    public final static int MINUS              = 91;
    public final static int TIMES              = 92;
    public final static int DIVIDE             = 93;
    public final static int MOD                = 94;
    public final static int ASSIGN             = 95;
    public final static int PLUSASSIGN         = 96;
    public final static int MINUSASSIGN        = 97;
    public final static int TIMESASSIGN        = 98;
    public final static int DIVIDEASSIGN       = 99;
    public final static int MODASSIGN          = 100;
    public final static int PLUSPLUS           = 101;
    public final static int MINUSMINUS         = 102;
    
    public final static int GREATER            = 110;
    public final static int LESSTHAN           = 111;
    public final static int GREATEREQ          = 112;
    public final static int LESSEQ             = 113;
    public final static int EQUAL              = 114;
    public final static int AND                = 115;
    public final static int OR                 = 116;
    public final static int NOTEQUAL           = 117;
    public final static int ANTI               = 118;
    
    /* Misc */
    public final static int DATA_TYPE_NAME     = 130;
    
    public final static int IDENTIFIER         = 132;
    public final static int POINTER_IDENTIFIER = 133;
    
    public final static int SEMICOLON          = 134;
    public final static int COMMA              = 135;
    
    /* Datas */
    public final static int DATA_CHAR          = 170;
    public final static int DATA_INTEGER       = 171;
    public final static int DATA_HEX           = 172;
    public final static int DATA_STRING        = 173;
    public final static int DATA_BOOLEAN       = 174;
    
    /* Main */
    private final String    matched;
    private final int       code;
    
    public Token(String matched, int code)
    {
        this.matched = matched;
        this.code = code;
    }
    
    public String getMatched()
    {
        return matched;
    }
    
    public int code()
    {
        return getCode();
    }
    
    public int getCode()
    {
        return code;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result + (matched == null ? 0 : matched.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Token other = (Token) obj;
        if (code != other.code)
        {
            return false;
        }
        if (matched == null)
        {
            if (other.matched != null)
            {
                return false;
            }
        }
        else if (!matched.equals(other.matched))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [");
        if (matched != null)
        {
            builder.append("matched='");
            builder.append(matched);
            builder.append("', ");
        }
        builder.append("code=");
        builder.append(code);
        builder.append("]");
        return builder.toString();
    }
}
