package com.kokakiwi.dev.tenc.core.parser;

import java.io.IOException;

/**
 * Scanner for the C programming language.
 *
 * @author Koka El Kiwi (and some codes by Robert Futrell)
 * @version 0.1
 *
 */
%%

%public
%class TencTokenMaker
%extends TokenParser
%unicode
%int
%char

%{
    public int parse() throws IOException
    {
        return yylex();
    }
%}

Letter                           = [A-Za-z]
LetterOrUnderscore               = ({Letter}|[_])
Digit                            = [0-9]
HexDigit                         = {Digit}|[A-Fa-f]
OctalDigit                       = [0-7]
Exponent                         = [eE][+-]?{Digit}+

LineTerminator                   = \n
WhiteSpace                       = [ \t\f]

Trigraph                         = ("??="|"??("|"??)"|"??/"|"??'"|"??<"|"??>"|"??!"|"??-")

OctEscape1                       = ([\\]{OctalDigit})
OctEscape2                       = ([\\]{OctalDigit}{OctalDigit})
OctEscape3                       = ([\\][0-3]{OctalDigit}{OctalDigit})
OctEscape                        = ({OctEscape1}|{OctEscape2}|{OctEscape3})
HexEscape                        = ([\\][xX]{HexDigit}{HexDigit})

AnyChrChr                        = ([^\'\n\\])
Escape                           = ([\\]([abfnrtv\'\"\?\\0]))
UnclosedCharLiteral              = ([\']({Escape}|{OctEscape}|{HexEscape}|{Trigraph}|{AnyChrChr}))
CharLiteral                      = ({UnclosedCharLiteral}[\'])
ErrorUnclosedCharLiteral         = ([\'][^\'\n]*)
ErrorCharLiteral                 = (([\'][\'])|{ErrorUnclosedCharLiteral}[\'])
AnyStrChr                        = ([^\"\n\\])
FalseTrigraph                    = (("?"(("?")*)[^\=\(\)\/\'\<\>\!\-\\\?\"\n])|("?"[\=\(\)\/\'\<\>\!\-]))
StringLiteral                    = ([\"]((((("?")*)({Escape}|{OctEscape}|{HexEscape}|{Trigraph}))|{FalseTrigraph}|{AnyStrChr})*)(("?")*)[\"])
UnclosedStringLiteral            = ([\"]([\\].|[^\\\"])*[^\"]?)
ErrorStringLiteral               = ({UnclosedStringLiteral}[\"])
Boolean                          = ("true"|"false")

NonFloatSuffix                   = (([uU][lL]?)|([lL][uU]?))
IntegerLiteral                   = ({Digit}+{Exponent}?{NonFloatSuffix}?)
HexLiteral                       = ("0"[xX]{HexDigit}+{NonFloatSuffix}?)
FloatLiteral                     = ((({Digit}*[\.]{Digit}+)|({Digit}+[\.]{Digit}*)){Exponent}?[fFlL]?)
ErrorNumberFormat                = (({IntegerLiteral}|{HexLiteral}|{FloatLiteral}){NonSeparator}+)

NonSeparator                     = ([^\t\f\r\n\ \(\)\{\}\[\]\;\,\.\=\>\<\!\~\?\:\+\-\*\/\&\|\^\%\"\']|"#")
Identifier                       = ({LetterOrUnderscore}({LetterOrUnderscore}|{Digit}|[$])*)
ErrorIdentifier                  = ({NonSeparator}+)
PointerIdentifier                = ({Identifier}\*)

DataType                         = ("int"|"char"|"boolean"|"void")

MLCBegin                         = "/*"
MLCEnd                           = "*/"
LineCommentBegin                 = "//"

%state MLC
%state EOL_COMMENT
%state VAR_DECLARE

%%

<YYINITIAL> {
    
    "{"                             { addToken(Token.OPEN_BRACK); }
    "}"                             { addToken(Token.CLOSE_BRACK); }
    "("                             { addToken(Token.OPEN_PAREN); }
    ")"                             { addToken(Token.CLOSE_PAREN); }
    "["                             { addToken(Token.OPEN_SQUARE); }
    "]"                             { addToken(Token.CLOSE_SQUARE); }
    
    ","                             { addToken(Token.COMMA); }
    
    /* Data types */
    {DataType}                      { addToken(Token.DATA_TYPE_NAME); }
    
    /* Operator */
    "+"                             { addToken(Token.PLUS); }
    "-"                             { addToken(Token.MINUS); }
    "*"                             { addToken(Token.TIMES); }
    "/"                             { addToken(Token.DIVIDE); }
    "%"                             { addToken(Token.MOD); }
    "="                             { addToken(Token.ASSIGN); }
    "+="                            { addToken(Token.PLUSASSIGN); }
    "-="                            { addToken(Token.MINUSASSIGN); }
    "*="                            { addToken(Token.TIMESASSIGN); }
    "/="                            { addToken(Token.DIVIDEASSIGN); }
    "%="                            { addToken(Token.MODASSIGN); }
    "++"                            { addToken(Token.PLUSPLUS); }
    "--"                            { addToken(Token.MINUSMINUS); }
    
    /* Comparators */
    ">"                             { addToken(Token.GREATER); }
    "<"                             { addToken(Token.LESSTHAN); }
    ">="                            { addToken(Token.GREATEREQ); }
    "<="                            { addToken(Token.LESSEQ); }
    "=="                            { addToken(Token.EQUAL); }
    "&&"                            { addToken(Token.AND); }
    "||"                            { addToken(Token.OR); }
    "!="                            { addToken(Token.NOTEQUAL); }
    "!"                             { addToken(Token.ANTI); }

    /* String/Character Literals. */
    {CharLiteral}                   { addToken(Token.DATA_CHAR); }
    {UnclosedCharLiteral}           { addToken(Token.UNKNOWN); }
    {ErrorUnclosedCharLiteral}      { addToken(Token.UNKNOWN); }
    {ErrorCharLiteral}              { addToken(Token.UNKNOWN); }
    {StringLiteral}                 { addToken(Token.DATA_STRING); }
    {UnclosedStringLiteral}         { addToken(Token.UNKNOWN); }
    {ErrorStringLiteral}            { addToken(Token.UNKNOWN); }

    /* Numbers */
    {IntegerLiteral}                { addToken(Token.DATA_INTEGER); }
    {HexLiteral}                    { addToken(Token.DATA_HEX); }
    {FloatLiteral}                  {  }
    {ErrorNumberFormat}             {  }
    {Boolean}                       { addToken(Token.DATA_BOOLEAN); }
    
    /* Blocks */
    "if"                            { addToken(Token.IF); }
    "else"                          { addToken(Token.ELSE); }
    "while"                         { addToken(Token.WHILE); }
    "return"                        { addToken(Token.RETURN); }
    "null"                          { addToken(Token.NULL); }

    {LineTerminator}                {  }
    {Identifier}                    { addToken(Token.IDENTIFIER); }
    /*{PointerIdentifier}             { addToken(Token.POINTER_IDENTIFIER); }*/
    {WhiteSpace}+                   {  }
    
    
    "\\"                            {  }
    {ErrorIdentifier}               {  }
    ";"                             { addToken(Token.SEMICOLON); }

    /* Ended with a line not in a string or comment. */
    <<EOF>>                         { return YYEOF; }

    /* Comments */
    {MLCBegin}                      { yybegin(MLC); }
    {LineCommentBegin}              { yybegin(EOL_COMMENT); }
    
    /* Catch any other (unhandled) characters and flag them as bad. */
    .                               {  }
}

<MLC> {
    [^hwf\n\*]+                 {  }
    [hwf]                       {  }
    
    \n                          {  }
    {MLCEnd}                    { yybegin(YYINITIAL); }
    \*                          {  }
    <<EOF>>                     { return YYEOF; }
}

<EOL_COMMENT> {
    [^hwf\n]+               {  }
    [hwf]                   {  }
    \n                      { yybegin(YYINITIAL); }
    <<EOF>>                 { return YYEOF; }
}