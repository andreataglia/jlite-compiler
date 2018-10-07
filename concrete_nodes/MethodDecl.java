package concrete_nodes;

import utils.BasicType;
import utils.FunctionType;

import java.util.HashMap;
import java.util.List;

public class MethodDecl extends Node {
    public FunctionType signature;
    public HashMap<BasicType, String> varDeclList;
    public List<Stmt> stmtList;
}
