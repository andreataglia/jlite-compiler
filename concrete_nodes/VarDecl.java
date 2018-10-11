package concrete_nodes;

import utils.BasicType;
import utils.Visitor;

public class VarDecl extends Node {
    public String id;
    public BasicType type;

    public VarDecl(String id, BasicType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * @param obj
     * @return true if the two VarDecl have the same name
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VarDecl) {
            VarDecl element = (VarDecl) obj;
            return this.id.equals(element.id);
        }
        return false;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return type + " " + id;
    }
}
