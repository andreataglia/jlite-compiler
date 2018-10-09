package concrete_nodes;

import utils.BasicType;
import utils.FunctionType;
import utils.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodDecl extends Node {
    public String name;
    public HashMap<String, BasicType> params;
    public BasicType returnType;
    public HashMap<String, BasicType> varDeclList;
    public List<Stmt> stmtList;

    public MethodDecl(String name, HashMap<String, BasicType> params, BasicType returnType, HashMap<String, BasicType> varDeclList, List<Stmt> stmtList) {
        this.name = name;
        this.params = params;
        this.returnType = returnType;
        if (varDeclList == null) {
            this.varDeclList = new HashMap<>();
        } else {
            this.varDeclList = varDeclList;
        }
        if (stmtList == null) {
            this.stmtList = new ArrayList<>();
        } else {
            this.stmtList = stmtList;
        }
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
