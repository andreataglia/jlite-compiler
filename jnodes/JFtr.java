package jnodes;

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
}
