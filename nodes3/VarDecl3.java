package nodes3;

import concrete_nodes.VarDecl;

public class VarDecl3 {
    public Type3 type;
    public Id3 id;

    public VarDecl3(Type3 type, Id3 id) {
        this.type = type;
        this.id = id;
    }

    public VarDecl3(VarDecl varDecl) {
        this.type = new Type3(varDecl.type);
        this.id = new Id3(varDecl.id);
    }

    @Override
    public String toString() {
        return type.type + " " + id.id;
    }
}
