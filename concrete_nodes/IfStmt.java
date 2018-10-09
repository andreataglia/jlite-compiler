package concrete_nodes;

import concrete_nodes.expressions.Expr;
import utils.Visitor;

import java.util.List;

public class IfStmt extends Stmt {
    public Expr condition;
    public List<Stmt> trueBranch;
    public List<Stmt> falseBranch;

    public IfStmt(Expr condition, List<Stmt> trueBranch, List<Stmt> falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
