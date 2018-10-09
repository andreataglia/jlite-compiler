package concrete_nodes;

import concrete_nodes.expressions.Atom;
import concrete_nodes.expressions.Expr;
import utils.Visitor;

public class AssignmentStmt extends Stmt {
    public String leftSideId;
    public Atom leftSideAtom;
    public Expr rightSide;

    public AssignmentStmt(String leftSideId, Expr rightSide) {
        this.leftSideId = leftSideId;
        this.rightSide = rightSide;
    }

    public AssignmentStmt(String leftSideId, Atom leftSideAtom, Expr rightSide) {
        this(leftSideId, rightSide);
        this.leftSideAtom = leftSideAtom;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
