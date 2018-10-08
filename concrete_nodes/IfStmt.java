package concrete_nodes;

import utils.Visitor;

import java.util.List;

public class IfStmt extends Stmt {
    public Expr condition;
    public List<Stmt> trueBranch;
    public List<Stmt> falseBranch;

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
