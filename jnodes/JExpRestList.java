package jnodes;

import concrete_nodes.expressions.Expr;

import java.util.ArrayList;

// expRestList ::=
//              | expRest expRestList
public class JExpRestList extends JNode {
    public JExpRest expRest;
    public JExpRestList expRestList;

    public JExpRestList(JExpRest expRest, JExpRestList expRestList) {
        this.expRest = expRest;
        this.expRestList = expRestList;
    }

    public JExpRestList() {
    }

    @Override
    public String toString() {
        return expRest != null ? expRest + " " + expRestList : "";
    }

    ArrayList<Expr> getExprList(){
        ArrayList<Expr> list = new ArrayList<>();
        if (expRest != null){
            list.add(expRest.getConcreteExpr());
            list.addAll(expRestList.getExprList());
        }
        return list;
    }
}
