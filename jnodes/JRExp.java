package jnodes;

// rExp ::= aExp bOp aExp | bGrd | NOT atom
public class JRExp extends JNode{
    public JAExp aexp1;
    public JAExp aexp2;
    public String bOp;
    public JAtom atom;
    public JBGrd bGrd;

    String print;

    public JRExp(JAExp aexp1, String bOp , JAExp aexp2) {
        this.aexp1 = aexp1;
        this.bOp = bOp;
        this.aexp2 = aexp2;

        print = aexp1 + bOp + aexp2;
    }

    public JRExp(JBGrd bGrd) {
        this.bGrd = bGrd;
        print = bGrd.toString();
    }

    public JRExp(JAtom atom) {
        this.atom = atom;
        print = "!" + atom;
    }

    @Override
    public String toString() {
        return print;
    }
}
