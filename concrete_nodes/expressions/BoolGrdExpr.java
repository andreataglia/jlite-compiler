package concrete_nodes.expressions;

import utils.Visitor;

public class BoolGrdExpr extends BoolExpr {
    //!<Grd>
    public BoolGrdExpr grdExpr;

    //<true> <false>
    public Boolean boolGrd;

    //<Atom>
    public Atom atom;

    private String printString;

    public BoolGrdExpr(BoolGrdExpr grdExpr) {
        this.grdExpr = grdExpr;
        printString = "!" + grdExpr;
    }

    public BoolGrdExpr(Boolean boolGrd) {
        this.boolGrd = boolGrd;
        printString = boolGrd.toString();
    }

    public BoolGrdExpr(Atom atom) {
        this.atom = atom;
        printString = atom.toString();
    }

    public boolean isGround(){
        return boolGrd != null;
    }

    public boolean isNegatedGround(){
        return grdExpr != null;
    }

    public boolean isAtomGround(){
        return atom != null;
    }

    @Override
    public String toString() {
        return printString;
    }

    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }
}
