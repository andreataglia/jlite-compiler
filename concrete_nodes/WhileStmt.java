package concrete_nodes;

import concrete_nodes.expressions.Expr;
import utils.Visitor;

import java.util.List;

public class WhileStmt extends Stmt {
    public Expr condition;
    public List<Stmt> body;

    public WhileStmt(Expr condition, List<Stmt> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
