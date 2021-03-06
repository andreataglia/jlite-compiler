package jnodes;


import concrete_nodes.expressions.BoolGrdExpr;

//bGrd ::= NOT bGrd | TRUE | FALSE
public class JBGrd extends JNode {
    public JBGrd jbGrd;
    public String bool;

    private BoolGrdExpr boolGrdExpr;

    public JBGrd(String bool) {
        this.bool = bool;
        boolGrdExpr = new BoolGrdExpr(Boolean.valueOf(bool));
    }

    public JBGrd(JBGrd jbGrd) {
        this.jbGrd = jbGrd;
        boolGrdExpr = new BoolGrdExpr(jbGrd.getConcreteNode());
        System.out.println(">>>>>>>>>>>>>>>>>>> created JBgrd " + toString());
    }

    @Override
    public String toString() {
        return jbGrd == null ? bool : "!" + jbGrd;
    }

    BoolGrdExpr getConcreteNode(){
        return boolGrdExpr;
    }
}
