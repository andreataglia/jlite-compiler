package jnodes;


import concrete_nodes.expressions.ArithExpr;
import concrete_nodes.expressions.TwoFactorsArithExpr;
import utils.ArithOperand;

//aExp ::= aExp aOp ftr | ftr
public class JAExp extends JExp {
    public JAExp aexp;
    public JFtr ftr;
    public String aOp;

    String print;

    public JAExp(JFtr ftr) {
        this.ftr = ftr;
        print = ftr.toString();
    }

    public JAExp(JAExp aexp, String aOp, JFtr ftr) {
        this.aexp = aexp;
        this.ftr = ftr;
        this.aOp = aOp;

        print = aexp + aOp + ftr;
    }

    @Override
    public String toString() {
        return print;
    }

    @Override
    ArithExpr getConcreteExpr() {
        ArithExpr arithExpr = null;
        if (aOp != null){
            arithExpr = new TwoFactorsArithExpr(ArithOperand.fromString(aOp), aexp.getConcreteExpr(), ftr.getConcreteNode());
        }else{
            arithExpr = ftr.getConcreteNode();
        }
        return arithExpr;
    }
}
