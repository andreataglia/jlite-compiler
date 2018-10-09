package jnodes;

import concrete_nodes.expressions.ArithGrdExpr;

// ftr ::= INTEGER_LITERAL
//        | MINUS ftr
//        | atom
public class JFtr extends JNode {
    public Integer integer;
    public JFtr ftr;
    public JAtom atom;

    String print;

    public JFtr(Integer integer) {
        this.integer = integer;

        print = String.valueOf(integer);
    }

    public JFtr(JFtr ftr) {
        this.ftr = ftr;

        print = "-" + ftr;
    }

    public JFtr(JAtom atom) {
        this.atom = atom;

        print = atom.toString();
    }

    @Override
    public String toString() {
        return print;
    }

    ArithGrdExpr getConcreteNode(){
        if (integer != null){
            return new ArithGrdExpr(integer);
        }else if (ftr != null){
            return new ArithGrdExpr(ftr.getConcreteNode());
        }else if (atom != null){
            return new ArithGrdExpr(atom.getConcreteNode());
        }
        return null;
    }
}
