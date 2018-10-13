package nodes3;

import utils.BoolOperand;

import java.util.List;

//⟨idc3⟩ ⟨Bop3⟩ ⟨idc3⟩ | ⟨Uop3⟩ ⟨idc3⟩ | ⟨id3⟩.⟨id3⟩ | ⟨idc3⟩
//| ⟨id3⟩( ⟨VList3⟩ ) | new ⟨CName3⟩()
public class Exp3 extends Node3 {
    public Idc3 idc3_1;
    public Idc3 idc3_2;
    public BoolOperand uOp3;

    public Id3 id3_1;
    public Id3 id3_2;

    public List<Idc3> params;

    public CName3 cName3;

    public Exp3(Idc3 idc3_1, BoolOperand uOp3, Idc3 idc3_2) {
        this.idc3_1 = idc3_1;
        this.idc3_2 = idc3_2;
        this.uOp3 = uOp3;
    }

    public Exp3(Idc3 idc3_1, BoolOperand uOp3) {
        this.idc3_1 = idc3_1;
        this.uOp3 = uOp3;
    }

    public Exp3(Id3 id3_1, Id3 id3_2) {
        this.id3_1 = id3_1;
        this.id3_2 = id3_2;
    }

    public Exp3(Idc3 idc3_1) {
        this.idc3_1 = idc3_1;
    }

    public Exp3(Id3 id3_1, List<Idc3> params) {
        this.id3_1 = id3_1;
        this.params = params;
    }

    public Exp3(CName3 cName3) {
        this.cName3 = cName3;
    }
}
