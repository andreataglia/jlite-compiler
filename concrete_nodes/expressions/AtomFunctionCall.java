package concrete_nodes.expressions;

import utils.Visitor;

import java.util.List;

public class AtomFunctionCall extends Atom{
    public Atom atom;
    public List<Expr> paramsList;

    public AtomFunctionCall(Atom atom, List<Expr> paramsList) {
        this.atom = atom;
        this.paramsList = paramsList;
    }

    @Override
    public String toString() {
        String s = atom + "(";
        boolean firstParam = true;
        if (!paramsList.isEmpty()){
            for (Expr e: paramsList) {
                if (!firstParam) s+= ", ";
                s += e.toString();
                firstParam = false;
            }
        }
        return s + ")";
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
