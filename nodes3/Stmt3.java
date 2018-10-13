package nodes3;

import java.util.List;

public class Stmt3 extends Node3 {
    Stmt3Type stmtType;
    String string;
    Id3 id3_1;
    Id3 id3_2;
    Exp3 exp3;
    VarDecl3 varDecl3;
    RelExp3 relExp3;
    Idc3 idc3;
    List<Idc3> params;

    public Stmt3(Stmt3Type stmtType, String string) {
        if (!(stmtType.equals(Stmt3Type.LABEL) || stmtType.equals(Stmt3Type.GOTO))) System.err.println("WARNING: wrong init Stmt3");
        this.stmtType = stmtType;
        this.string = string;
    }

    public Stmt3(Stmt3Type stmtType, String string, RelExp3 relExp3) {
        if (!(stmtType.equals(Stmt3Type.IF))) System.err.println("WARNING: wrong init Stmt3");
        this.stmtType = stmtType;
        this.string = string;
        this.relExp3 = relExp3;
    }

    public Stmt3(Stmt3Type stmtType, Id3 id3_1) {
        if (!(stmtType.equals(Stmt3Type.READLN))) System.err.println("WARNING: wrong init Stmt3");
        this.stmtType = stmtType;
        this.id3_1 = id3_1;
    }

    public Stmt3(Stmt3Type stmtType, Idc3 idc3) {
        if (!(stmtType.equals(Stmt3Type.PRINTLN))) System.err.println("WARNING: wrong init Stmt3");
        this.stmtType = stmtType;
        this.idc3 = idc3;
    }

    public Stmt3(Stmt3Type stmtType, Exp3 exp3, VarDecl3 varDecl3) {
        if (!(stmtType.equals(Stmt3Type.ASS_VARDECL))) System.err.println("WARNING: wrong init Stmt3");
        this.stmtType = stmtType;
        this.exp3 = exp3;
        this.varDecl3 = varDecl3;
    }


    public Stmt3(Stmt3Type stmtType, Id3 id3_1, Exp3 exp3) {
        if (!(stmtType.equals(Stmt3Type.ASS_VAR))) System.err.println("WARNING: wrong init Stmt3");
        this.stmtType = stmtType;
        this.id3_1 = id3_1;
        this.exp3 = exp3;
    }

    //⟨Label3⟩ : | if ( ⟨RelExp3⟩ ) goto ⟨Label3⟩ ; | goto ⟨Label3⟩ ;
    //| readln ( ⟨id3⟩ ) ; | println ( ⟨idc3⟩ ) ;
    //| ⟨Type3⟩ ⟨id3⟩ = ⟨Exp3⟩ ; | ⟨id3⟩ = ⟨Exp3⟩ ; | ⟨id3⟩.⟨id3⟩ = ⟨Exp3⟩ ;
    //| ⟨id3⟩( ⟨VList3⟩ ) ;
    //| return ⟨id3⟩ ; | return ;
    public enum Stmt3Type{
        LABEL,
        GOTO,
        IF,
        READLN,
        PRINTLN,
        ASS_VARDECL,
        ASS_VAR,
        ASS_FIELD, //TODO constructor impl
        FUNCTION,
        RETURN,
        RETURN_VAR;

        public boolean equals(Stmt3Type other){
            return this.name().equals(other.name());
        }
    }
}
