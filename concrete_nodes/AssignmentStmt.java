package concrete_nodes;

import concrete_nodes.expressions.Atom;
import concrete_nodes.expressions.AtomGrd;
import concrete_nodes.expressions.Expr;
import utils.Visitor;

public class AssignmentStmt extends Stmt {
    public AtomGrd leftSideId;
    public Atom leftSideAtom;
    public Expr rightSide;

    //<id> = <Exp>
    public AssignmentStmt(AtomGrd leftSideId, Expr rightSide) {
        this.leftSideId = leftSideId;
        this.rightSide = rightSide;
    }

    //<Atom>.<id> = <Exp>
    public AssignmentStmt(AtomGrd leftSideId, Atom leftSideAtom, Expr rightSide) {
        this(leftSideId, rightSide);
        this.leftSideAtom = leftSideAtom;
    }

    public boolean isSimpleAssignment(){
        return leftSideAtom == null;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }
}
