package nodes3;

import utils.BoolOperand;

import java.util.List;

//⟨idc3⟩ ⟨Bop3⟩ ⟨idc3⟩ | ⟨Uop3⟩ ⟨idc3⟩ | ⟨id3⟩.⟨id3⟩ | ⟨idc3⟩
//| ⟨id3⟩( ⟨VList3⟩ ) | new ⟨CName3⟩()
public class Exp3Impl extends Exp3 {
    public Idc3 idc3_1;
    public Idc3 idc3_2;
    public BoolOperand uOp3;

    public Id3 id3_1;
    public Id3 id3_2;

    public List<Idc3> params;

    public CName3 cName3;

    //⟨idc3⟩ ⟨Bop3⟩ ⟨idc3⟩
    public Exp3Impl(Idc3 idc3_1, BoolOperand uOp3, Idc3 idc3_2) {
        this.idc3_1 = idc3_1;
        this.idc3_2 = idc3_2;
        this.uOp3 = uOp3;
    }

    //⟨id3⟩.⟨id3⟩
    public Exp3Impl(Id3 id3_1, Id3 id3_2) {
        this.id3_1 = id3_1;
        this.id3_2 = id3_2;
    }

    //⟨Uop3⟩ ⟨idc3⟩
    public Exp3Impl(Idc3 idc3_1) {
        this.idc3_1 = idc3_1;
    }

    //⟨id3⟩( ⟨VList3⟩ )
    public Exp3Impl(Id3 id3_1, List<Idc3> params) {
        this.id3_1 = id3_1;
        this.params = params;
    }

    //new ⟨CName3⟩()
    public Exp3Impl(CName3 cName3) {
        this.cName3 = cName3;
    }


    @Override
    public String toString() {
        return "TODO"; //TODO
    }
}
