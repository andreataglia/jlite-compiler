package concrete_nodes.expressions;

import utils.ClassNameType;
import utils.Visitor;

import java.util.List;

public class AtomFunctionCall extends Atom{
    public Atom functionId;
    public List<Expr> paramsList;

    public ClassNameType classFunctionOwner;
    public String functionName;

    public AtomFunctionCall(Atom functionId, List<Expr> paramsList) {
        this.functionId = functionId;
        this.paramsList = paramsList;
    }

    @Override
    public String toString() {
        String s = functionId + "(";
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
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
