package concrete_nodes;

import concrete_nodes.expressions.AtomFunctionCall;
import utils.Visitor;

public class FunctionCallStmt extends Stmt{
    public AtomFunctionCall atom;

    public FunctionCallStmt(AtomFunctionCall atom) {
        this.atom = atom;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }
}
