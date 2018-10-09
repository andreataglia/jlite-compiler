package concrete_nodes.expressions;

import utils.ClassNameType;
import utils.Visitor;

public class AtomClassInstantiation extends Atom {
    public ClassNameType cname;

    public AtomClassInstantiation(ClassNameType cname) {
        this.cname = cname;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "new " + cname +"()";
    }
}
