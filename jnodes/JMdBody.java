package jnodes;

import concrete_nodes.Stmt;
import utils.BasicType;

import java.util.ArrayList;
import java.util.HashMap;

//mdBody ::= LBRACE varDeclList stmt stmtList RBRACE
public class JMdBody extends JNode{
    public JVarDeclList varDeclList;
    public JStmt stmt;
    public JStmtList stmtList;

    public JMdBody(JVarDeclList varDeclList, JStmt stmt, JStmtList stmtList) {
        this.varDeclList = varDeclList;
        this.stmt = stmt;
        this.stmtList = stmtList;
    }

    @Override
    public String toString() {
        return "{\n    " + varDeclList + stmt + stmtList + "}";
    }

    HashMap<String, BasicType> getVarDeclList(){
        HashMap<String, BasicType> map = new HashMap<>();
        if (varDeclList != null){
            for (JVarDecl var: varDeclList.getVarsList()) {
                map.put(var.id.s, var.type.basicType);
            }
        }
        return map;
    }

    ArrayList<Stmt> getStmts(){
        ArrayList<Stmt> list = new ArrayList<>();
        list.add(stmt.getConcreteStmt());
        list.addAll(stmtList.getStmtList());
        return list;
    }

}
