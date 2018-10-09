package concrete_nodes;

import utils.Visitor;

public class WhileStmt extends Stmt {
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
