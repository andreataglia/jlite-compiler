package nodes3;

import java.util.List;

public class CMtd3 extends Node3 {
    Type3 returnType;
    Id3 name;
    List<VarDecl3> params;

    //body
    List<VarDecl3> varDeclList;
    List<Stmt3> stmtList;

    public CMtd3(Type3 returnType, Id3 name, List<VarDecl3> params, List<VarDecl3> varDeclList, List<Stmt3> stmtList) {
        this.returnType = returnType;
        this.name = name;
        this.params = params;
        this.varDeclList = varDeclList;
        this.stmtList = stmtList;
    }
}

