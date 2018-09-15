package jnodes;


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
}
