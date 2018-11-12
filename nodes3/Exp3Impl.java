package nodes3;

import asm.ASMGeneratorVisitor;
import utils.ArithOperand;
import utils.BoolOperand;

import java.util.List;

//   ⟨idc3⟩ ⟨Bop3⟩ ⟨idc3⟩
// | ⟨Uop3⟩ ⟨idc3⟩
// | ⟨id3⟩.⟨id3⟩
// | ⟨idc3⟩
// | ⟨id3⟩( ⟨VList3⟩ )
// | new ⟨CName3⟩()
public class Exp3Impl extends Exp3 {
    public Idc3 idc3_1;
    public Idc3 idc3_2;
    public BoolOperand bOp3;
    public ArithOperand arithOp3;
    public String uOp3;

    public Id3 id3_1;
    public Id3 id3_2;
    public Type type;

    public List<Idc3> params;

    public CName3 cName3;

    private String print;

    //⟨idc3⟩ ⟨Bop3⟩ ⟨idc3⟩
    public Exp3Impl(Type3 type, Idc3 idc3_1, BoolOperand bOp3, Idc3 idc3_2) {
        super(type);
        this.idc3_1 = idc3_1;
        this.idc3_2 = idc3_2;
        this.bOp3 = bOp3;

        print = idc3_1 + " " + bOp3 + " " + idc3_2;
        this.type = Type.BOOLEAN_EXP;
    }

    //⟨idc3⟩ ⟨arithOp3⟩ ⟨idc3⟩
    public Exp3Impl(Type3 type, Idc3 idc3_1, ArithOperand arithOp3, Idc3 idc3_2) {
        super(type);
        this.idc3_1 = idc3_1;
        this.idc3_2 = idc3_2;
        this.arithOp3 = arithOp3;

        print = idc3_1 + " " + arithOp3 + " " + idc3_2;
        this.type = Type.ARITH_EXP;
    }

    //⟨id3⟩.⟨id3⟩
    public Exp3Impl(Type3 type, Id3 id3_1, Id3 id3_2) {
        super(type);
        this.id3_1 = id3_1;
        this.id3_2 = id3_2;

        print = id3_1 + "." + id3_2;
        this.type = Type.FIEL_DACCESS;
    }

    //⟨Uop3⟩ ⟨idc3⟩
    public Exp3Impl(Type3 type, String uOp3, Idc3 idc3_1) {
        super(type);
        this.uOp3 = uOp3;
        this.idc3_1 = idc3_1;
        print = uOp3 + idc3_1;
        this.type = Type.UOP;
    }

    //⟨id3⟩( ⟨VList3⟩ )  -- note that first param is the object on which the function call is executed
    public Exp3Impl(Type3 type, Id3 id3_1, List<Idc3> params) {
        super(type);
        this.id3_1 = id3_1;
        this.params = params;
        print = id3_1 + "(";
        boolean firstParam = true;
        for (Idc3 p : params) {
            if (!firstParam) print += ", ";
            print += p;
            firstParam = false;
        }
        print += ")";
        this.type = Type.FUNCTIONCALL;
    }

    //new ⟨CName3⟩()
    public Exp3Impl(Type3 type, CName3 cName3) {
        super(type);
        this.cName3 = cName3;

        print = "new " + cName3 + "()";
        this.type = Type.NEWOBJECT;
    }

    public enum Type{
        BOOLEAN_EXP,
        ARITH_EXP,
        FIEL_DACCESS,
        UOP,
        FUNCTIONCALL,
        NEWOBJECT;

        public boolean equals(Type other){
            return this.name().equals(other.name());
        }
    }


    @Override
    public String toString() {
        return print;
    }

    public int accept(ASMGeneratorVisitor visitor){
        return visitor.visit(this);
    }
}
