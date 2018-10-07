package concrete_nodes;

import utils.BasicType;
import utils.ClassNameType;

import java.util.HashMap;
import java.util.List;

public class ClassDecl extends Node {
    public ClassNameType className;
    public HashMap<BasicType, String> varDeclList;
    public List<MethodDecl> methodDeclList;
}
