package concrete_nodes;

import concrete_nodes.expressions.Atom;
import concrete_nodes.expressions.Expr;
import utils.Visitor;

import java.util.List;

public class FunctionCallStmt extends Stmt{
    public Atom atom;
    public List<Expr> paramsList;

    public FunctionCallStmt(Atom atom, List<Expr> paramsList) {
        this.atom = atom;
        this.paramsList = paramsList;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }
}
