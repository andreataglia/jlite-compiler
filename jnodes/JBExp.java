package jnodes;

import concrete_nodes.expressions.BoolExpr;
import concrete_nodes.expressions.BoolGrdExpr;
import concrete_nodes.expressions.TwoFactorsBoolExpr;
import utils.BoolOperand;

//bExp ::= bExp OR conj
//        | conj
//        | bExp OR functionId
//        | functionId OR functionId
//        | functionId OR conj
public class JBExp extends JExp {
    //left side
    public JBExp bExp;
    public JAtom atom1;
    public JConj conj1;

    //right side
    public JConj conj2;
    public JAtom atom2;

    private String print;

    private BoolExpr boolExpr;

    public JBExp(JBExp bExp, JConj conj2) {
        this.bExp = bExp;
        this.conj2 = conj2;
        print = bExp + " OR " + conj2;
        boolExpr = new TwoFactorsBoolExpr(bExp.getConcreteExpr(), BoolOperand.OR, conj2.getConcreteExpr());
    }

    public JBExp(JBExp bExp, JAtom atom2) {
        this.bExp = bExp;
        this.atom2 = atom2;
        print = bExp + " OR " + atom2;
        boolExpr = new TwoFactorsBoolExpr(bExp.getConcreteExpr(), BoolOperand.OR, new BoolGrdExpr(atom2.getConcreteNode()));
    }

    public JBExp(JConj conj1) {
        this.conj1 = conj1;
        print = conj1.toString();
        boolExpr = conj1.getConcreteExpr();
    }

    public JBExp(JAtom atom1, JConj conj2) {
        this.atom1 = atom1;
        this.conj2 = conj2;
        print = atom1 + " OR " + conj2;
        boolExpr = new TwoFactorsBoolExpr(new BoolGrdExpr(atom1.getConcreteNode()), BoolOperand.OR, conj2.getConcreteExpr());
    }

    public JBExp(JAtom atom1, JAtom atom2) {
        this.atom1 = atom1;
        this.atom2 = atom2;
        print = atom1 + " OR " + atom2;

        boolExpr = new TwoFactorsBoolExpr(new BoolGrdExpr(atom1.getConcreteNode()), BoolOperand.OR, new BoolGrdExpr(atom2.getConcreteNode()));
    }

    @Override
    public String toString() {
        return print;
    }

    @Override
    BoolExpr getConcreteExpr() {
        return boolExpr;
    }
}
