package concrete_nodes.expressions;

import utils.Visitor;

public class ArithGrdExpr extends ArithExpr {
    //<INTEGER_LITERAL>
    public Integer intLiteral;

    //<Atom>
    public Atom atom;

    //-<Ftr>
    public ArithGrdExpr negateFactor;

    public ArithGrdExpr(Integer intLiteral) {
        this.intLiteral = intLiteral;
    }

    public ArithGrdExpr(Atom atom) {
        this.atom = atom;
    }

    public ArithGrdExpr(ArithGrdExpr negateFactor) {
        this.negateFactor = negateFactor;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        if (intLiteral != null) {
            return String.valueOf(intLiteral);
        } else if (atom != null) {
            return atom.toString();
        } else if (negateFactor != null) {
            return "-" + negateFactor.toString();
        }
        return "WARNING: empty OneFactorArithExpr";
    }
}
