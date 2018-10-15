package concrete_nodes.expressions;

import utils.Visitor;

public class AtomFieldAccess extends Atom {
    public Atom atom;
    public String field;

    public AtomFieldAccess(Atom atom, String field) {
        this.atom = atom;
        this.field = field;
    }

    @Override
    public String toString() {
        return atom + "." + field;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
