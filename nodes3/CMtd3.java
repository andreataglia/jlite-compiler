package nodes3;

import java.util.List;

public class CMtd3 extends Node3 {
    public Type3 returnType;
    public Id3 name;
    public List<VarDecl3> params;

    //body
    public List<VarDecl3> varDeclList;
    public List<Stmt3> stmtList;

    //TODO parama is allowed to be NULL, but in IR3 specs doesn't allow a type to be null
    public CMtd3(Type3 returnType, Id3 name, List<VarDecl3> params, List<VarDecl3> varDeclList, List<Stmt3> stmtList) {
        this.returnType = returnType;
        this.name = name;
        this.params = params;
        this.varDeclList = varDeclList;
        this.stmtList = stmtList;
    }

    public String print() {
        System.out.print(returnType + " " + name + "(");
        for (VarDecl3 entry : params) {
            System.out.print(", " + entry.type + " " + entry.id);
        }
        System.out.print("){");
        if (!varDeclList.isEmpty()) {
            for (VarDecl3 entry : varDeclList) {
                System.out.print("\n    " + entry + ";");
            }
        }
        if (!stmtList.isEmpty()) {
            for (Stmt3 s : stmtList) {
                System.out.print("\n   " + s + ";");
            }
        }
        System.out.println("}");
        return super.toString();
    }
}

