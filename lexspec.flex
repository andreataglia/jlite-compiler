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
blank_line = ({eol}{space}*{eol})({eol}{space}*{eol})*

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
    "while" { System.out.print(yytext()); return symbol(sym.WHILE); }
    "return" { System.out.print(yytext()); return symbol(sym.RETURN); }
    
    /* declarations */
    "class" { System.out.print(yytext()); return symbol(sym.CLASS); }
    "Void" { System.out.print(yytext()); return symbol(sym.VOID); }
    "main" { System.out.print(yytext()); return symbol(sym.MAIN); }
    "this" { System.out.print(yytext()); return symbol(sym.THIS); }
    "new" { System.out.print(yytext()); return symbol(sym.NEW); }

    /* operators */
    "+" { System.out.print(yytext()); return symbol(sym.PLUS); }
    "-" { System.out.print(yytext()); return symbol(sym.MINUS); }
    "*" { System.out.print(yytext()); return symbol(sym.TIMES); }
    "&&" { System.out.print(yytext()); return symbol(sym.AND); }
    "||" { System.out.print(yytext()); return symbol(sym.OR); }
    "==" { System.out.print(yytext()); return symbol(sym.DEQUAL); }
    "<" { System.out.print(yytext()); return symbol(sym.LT); }
    ">" { System.out.print(yytext()); return symbol(sym.GT); }
    "<=" { System.out.print(yytext()); return symbol(sym.LET); }
    ">=" { System.out.print(yytext()); return symbol(sym.GET); }
    "!" { System.out.print(yytext()); return symbol(sym.NOT); }
    "." { System.out.print(yytext()); return symbol(sym.DOT); }
    "," { System.out.print(yytext()); return symbol(sym.COMMA); }
    "=" { System.out.print(yytext()); return symbol(sym.EQUAL); }
    "!=" { System.out.print(yytext()); return symbol(sym.DIFF); }

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
