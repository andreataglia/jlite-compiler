/*
  This example comes from a short article series in the Linux 
  Gazette by Richard A. Sevenich and Christopher Lopes, titled
  "Compiler Construction Tools". The article series starts at

  http://www.linuxgazette.com/issue39/sevenich.html

  Small changes and updates to newest JFlex+Cup versions 
  by Gerwin Klein
*/

/*
  Commented By: Christopher Lopes
  File Name: lcalc.flex
  To Create: > jflex lcalc.flex

  and then after the parser is created
  > javac Lexer.java
*/
   
/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;
      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* 
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java. 
*/
%class Lexer

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column
    
/* 
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup
   
/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{   
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }

    private String getIndentSpaces(){
        String spaces = "";
        for(short i = 0; i < indent; i++) {
          spaces += "   ";
        }
        return spaces;
    }

    private String s;
    private short indent = 0;
%}
   

/* helper definitions */
letter = [a-zA-Z]
lc_letter = [a-z]
uc_letter = [A-Z]
digit = [0-9]
eol = \r | \n | \r\n
not_eol = [^\r\n]
space = [ \t\f] | [ \t]
white = {eol} | {space} /* White space is a line terminator, space, tab, or line feed. */

start_comment = "/*"
comment_content = ([^*]|\*[^/])
end_comment = "*/"
   
%%

/* ------------------------ Token Definitions ---------------------- */
     
<YYINITIAL> {

    /* literals */    
    "0" | [1-9]{digit}*      { System.out.print(yytext()); return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); }
    "\"" .* "\""   { System.out.print(yytext()); return symbol(sym.STRING_LITERAL); }
    
    /* comments */
    "//"{not_eol}*{eol} { /* do nothing */ }
    {start_comment}{comment_content}*{end_comment} { /* do nothing */ }  

    /* types */
    "Bool" { System.out.print(yytext()); return symbol(sym.BOOL); }
    "Int" { System.out.print(yytext()); return symbol(sym.INT); }
    "true" { System.out.print(yytext()); return symbol(sym.TRUE); }
    "false" {System.out.print(yytext()); return symbol(sym.FALSE); }
    "String" {System.out.print(yytext()); return symbol(sym.STRING); }

    /* control flow */
    "if" {System.out.print(yytext()); return symbol(sym.IF); }
    "else" {System.out.print(yytext()); return symbol(sym.ELSE); }
    "while" { System.out.print("while"); return symbol(sym.WHILE); }
    "return" { System.out.print("return"); return symbol(sym.RETURN); }
    
    /* declarations */
    "class" { System.out.print("CLASS"); return symbol(sym.CLASS); }
    "Void" { System.out.print("VOID"); return symbol(sym.VOID); }
    "main" { System.out.print("MAIN"); return symbol(sym.MAIN); }
    "this" { System.out.print(yytext()); return symbol(sym.THIS); }
    "new" { System.out.print(yytext()); return symbol(sym.NEW); }

    /* operators */
    "+" { System.out.print(yytext()); return symbol(sym.PLUS); }
    "-" { System.out.print(yytext()); return symbol(sym.MINUS); }
    "*" { System.out.print(yytext()); return symbol(sym.TIMES); }
    "&&" { System.out.print(yytext()); return symbol(sym.AND); }
    "||" { System.out.print(yytext()); return symbol(sym.OR); }
    "<" { System.out.print(yytext()); return symbol(sym.LT); }
    "!" { System.out.print(yytext()); return symbol(sym.NOT); }
    "." { System.out.print(yytext()); return symbol(sym.DOT); }
    "," { System.out.print(yytext()); return symbol(sym.COMMA); }
    "=" { System.out.print(yytext()); return symbol(sym.EQUAL); }

    /* delimiters */
    "(" { System.out.print(yytext()); return symbol(sym.LPAREN); }
    ")" { System.out.print(yytext()); return symbol(sym.RPAREN); }
    "{" { System.out.print(yytext()); indent++; return symbol(sym.LBRACE); }
    "}" { System.out.print(yytext()); indent--; return symbol(sym.RBRACE); }
    ";" { System.out.print(yytext()); return symbol(sym.SEMICOLON); }

    /* identifiers */
    {lc_letter}({letter}|{digit}|_)*    { System.out.print(yytext()); s = new String(yytext()); s.toLowerCase(); return symbol(sym.ID, s);}
    {uc_letter}({letter}|{digit}|_)*    { System.out.print(yytext()); s = new String(yytext()); s.toLowerCase(); return symbol(sym.CNAME, s);}

    /* pretty printing utils*/
    {space}   {  System.out.print(" "); }
    {eol}   {  System.out.print("\n" + getIndentSpaces()); }
}

/* lexical errors (put last so other matches take precedence) */
/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^] {
    System.err.println(
        "Warning: ignoring invalid token at line " +
        (yyline + 1) +
        ", column " +
        (yycolumn + 1) +
        "."
    );
    // Do not return any tokens
}
