package jnodes;


//  stmt ::= IF LPAREN exp RPAREN LBRACE stmt stmtList RBRACE ELSE LBRACE stmt stmtList RBRACE
//       | WHILE LPAREN exp RPAREN LBRACE stmt stmtList RBRACE
//       | READLN LPAREN ident RPAREN SEMICOLON
//       | PRINTLN LPAREN exp RPAREN SEMICOLON
//       | ident EQUAL exp SEMICOLON
//       | atom DOT ident EQUAL exp SEMICOLON
//       | atom LPAREN expList RPAREN SEMICOLON
//       | RETURN exp SEMICOLON
//       | RETURN SEMICOLON
abstract public class JStmt extends JNode{

}
