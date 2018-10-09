package concrete_nodes.expressions;

import utils.Visitor;

public class AtomGrd extends Atom{
    public String id;

    public AtomGrd(String id) {
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
