/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;
      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
%class Lexer

%line
%column
    
%cup
   
%{   
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
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
blank_line = ({eol}{space}*{eol})({eol}{space}*{eol})*

start_comment = "/*"
comment_content = ([^*]|\*[^/])
end_comment = "*/"
   
%%

/* ------------------------ Token Definitions ---------------------- */
     
<YYINITIAL> {

    /* literals */    
    "0" | [1-9]{digit}*      { System.out.print(yytext()); return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); }
    "\"" .* "\""   { System.out.print(yytext()); return symbol(sym.STRING_LITERAL, yytext()); }
    
    /* comments */
    "//"{not_eol}*{eol} { /* do nothing */ }
    {start_comment}{comment_content}*{end_comment} { /* do nothing */ }  

    /* types */
    "Bool" { System.out.print(yytext()); return symbol(sym.BOOL, yytext()); }
    "Int" { System.out.print(yytext()); return symbol(sym.INT, yytext()); }
    "true" { System.out.print(yytext()); return symbol(sym.TRUE, yytext()); }
    "false" {System.out.print(yytext()); return symbol(sym.FALSE, yytext()); }
    "String" {System.out.print(yytext()); return symbol(sym.STRING, yytext()); }

    /* control flow */
    "if" {System.out.print(yytext()); return symbol(sym.IF, yytext()); }
    "else" {System.out.print(yytext()); return symbol(sym.ELSE, yytext()); }
    "while" { System.out.print(yytext()); return symbol(sym.WHILE, yytext()); }
    "return" { System.out.print(yytext()); return symbol(sym.RETURN, yytext()); }
    
    /* declarations */
    "class" { System.out.print(yytext()); return symbol(sym.CLASS, yytext()); }
    "Void" { System.out.print(yytext()); return symbol(sym.VOID, yytext()); }
    "main" { System.out.print(yytext()); return symbol(sym.MAIN, yytext()); }
    "this" { System.out.print(yytext()); return symbol(sym.THIS, yytext()); }
    "new" { System.out.print(yytext()); return symbol(sym.NEW, yytext()); }
    "null" { System.out.print(yytext()); return symbol(sym.NULL, yytext()); }
    "NULL" { System.out.print(yytext()); return symbol(sym.NULL, yytext()); }

    "println" { System.out.print(yytext()); return symbol(sym.PRINTLN, yytext()); }
    "readln" { System.out.print(yytext()); return symbol(sym.READLN, yytext()); }

    /* operators */
    "+" { System.out.print(yytext()); return symbol(sym.PLUS,yytext()); }
    "-" { System.out.print(yytext()); return symbol(sym.MINUS,yytext()); }
    "*" { System.out.print(yytext()); return symbol(sym.TIMES,yytext()); }
    "/" { System.out.print(yytext()); return symbol(sym.DIVIDE,yytext()); }
    "&&" { System.out.print(yytext()); return symbol(sym.AND,yytext()); }
    "||" { System.out.print(yytext()); return symbol(sym.OR,yytext()); }
    "==" { System.out.print(yytext()); return symbol(sym.DEQUAL,yytext()); }
    "<" { System.out.print(yytext()); return symbol(sym.LT,yytext()); }
    ">" { System.out.print(yytext()); return symbol(sym.GT,yytext()); }
    "<=" { System.out.print(yytext()); return symbol(sym.LET,yytext()); }
    ">=" { System.out.print(yytext()); return symbol(sym.GET,yytext()); }
    "!" { System.out.print(yytext()); return symbol(sym.NOT,yytext()); }
    "." { System.out.print(yytext()); return symbol(sym.DOT,yytext()); }
    "," { System.out.print(yytext()); return symbol(sym.COMMA,yytext()); }
    "=" { System.out.print(yytext()); return symbol(sym.EQUAL,yytext()); }
    "!=" { System.out.print(yytext()); return symbol(sym.DIFF,yytext()); }

    /* delimiters */
    "(" { System.out.print(yytext()); return symbol(sym.LPAREN, yytext()); }
    ")" { System.out.print(yytext()); return symbol(sym.RPAREN, yytext()); }
    "{" { System.out.print(yytext()); indent++; return symbol(sym.LBRACE, yytext()); }
    "}" { System.out.print(yytext()); indent--; return symbol(sym.RBRACE, yytext()); }
    ";" { System.out.print(yytext()); return symbol(sym.SEMICOLON, yytext()); }

    /* identifiers */
    {lc_letter}({letter}|{digit}|_)*    { System.out.print(yytext()); return symbol(sym.ID, yytext());}
    {uc_letter}({letter}|{digit}|_)*    { System.out.print(yytext()); return symbol(sym.CNAME, yytext());}

    /* pretty printing utils*/
    {space}({space})*   {  System.out.print(" "); }
    {blank_line} { System.out.print("\n" + getIndentSpaces()); }
    {eol}   {  System.out.print("\n" + getIndentSpaces()); }
}


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
