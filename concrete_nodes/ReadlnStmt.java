package concrete_nodes;

import concrete_nodes.expressions.AtomGrd;
import utils.Visitor;

public class ReadlnStmt extends Stmt{
    public AtomGrd id;

    public ReadlnStmt(AtomGrd id) {
        this.id = id;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }
}
