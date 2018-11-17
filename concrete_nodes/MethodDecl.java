package concrete_nodes;

import utils.BasicType;
import utils.FunctionType;
import utils.VarsList;
import utils.Visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodDecl extends Node {
    public String name;
    public VarsList params;
    public BasicType returnType;
    public VarsList varDeclList;
    public List<Stmt> stmtList;

    public String className;
    public int count;

    public MethodDecl(String name, VarsList params, BasicType returnType, VarsList varDeclList, List<Stmt> stmtList) {
        this.name = name;
        this.params = params;
        if (params == null) {
            this.params = new VarsList();
        }
        this.returnType = returnType;
        if (varDeclList == null) {
            this.varDeclList = new VarsList();
        } else {
            this.varDeclList = varDeclList;
        }
        if (stmtList == null) {
            this.stmtList = new ArrayList<>();
        } else {
            this.stmtList = stmtList;
        }
    }

    public MethodDecl(String name, List<VarDecl> params, BasicType returnType, List<VarDecl> varDeclList, List<Stmt> stmtList) {
        this.name = name;
        this.params = new VarsList(params);
        this.returnType = returnType;
        this.varDeclList = new VarsList(varDeclList);
        if (stmtList == null) {
            this.stmtList = new ArrayList<>();
        } else {
            this.stmtList = stmtList;
        }
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
