package jnodes;


import concrete_nodes.Stmt;

//  stmt ::= IF LPAREN exp RPAREN LBRACE stmt stmtList RBRACE ELSE LBRACE stmt stmtList RBRACE
//       | WHILE LPAREN exp RPAREN LBRACE stmt stmtList RBRACE
//       | READLN LPAREN ident RPAREN SEMICOLON
//       | PRINTLN LPAREN exp RPAREN SEMICOLON
//       | ident EQUAL exp SEMICOLON
//       | functionId DOT ident EQUAL exp SEMICOLON
//       | functionId LPAREN expList RPAREN SEMICOLON
//       | RETURN exp SEMICOLON
//       | RETURN SEMICOLON
abstract public class JStmt extends JNode{
    abstract Stmt getConcreteStmt();
}
