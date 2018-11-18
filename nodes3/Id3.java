package nodes3;

import asm.ASMGeneratorVisitor;

public class Id3 extends Idc3 {
    public String id;

    public Id3(Type3 type, String id) {
        super(type);
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    public int accept(ASMGeneratorVisitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
