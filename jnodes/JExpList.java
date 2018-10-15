package jnodes;

import concrete_nodes.expressions.Expr;

import java.util.ArrayList;

//expList ::= exp expRestList | ''
public class JExpList extends JNode{
    public JExp exp;
    public JExpRestList expRestList;

    public JExpList(JExp exp, JExpRestList expRestList) {
        this.exp = exp;
        this.expRestList = expRestList;
    }

    public JExpList(){

    }

    @Override
    public String toString() {
        return exp!=null? "" + exp + expRestList : "";
    }

    ArrayList<Expr> getExprList(){
        ArrayList<Expr> list = new ArrayList<>();
        if (exp != null){
            list.add(exp.getConcreteExpr());
            list.addAll(expRestList.getExprList());
        }
        return list;
    }
}
