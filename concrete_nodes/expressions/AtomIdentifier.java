package concrete_nodes.expressions;

import utils.Visitor;

public class AtomIdentifier extends Atom {
    public String id;

    public AtomIdentifier(String id) {
        this.id = id;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return id;
    }
}
