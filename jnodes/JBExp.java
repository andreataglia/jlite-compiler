package jnodes;

//bExp ::= bExp OR conj
//        | conj
//        | bExp OR atom
//        | atom OR atom
//        | atom OR conj
public class JBExp extends JExp {
    //left side
    public JBExp bExp;
    public JAtom atom1;
    public JConj conj1;

    //right side
    public JConj conj2;
    public JAtom atom2;

    String print;

    public JBExp(JBExp bExp, JConj conj2) {
        this.bExp = bExp;
        this.conj2 = conj2;
        print = bExp + " OR " + conj2;
    }

    public JBExp(JBExp bExp, JAtom atom2) {
        this.bExp = bExp;
        this.atom2 = atom2;
        print = bExp + " OR " + atom2;
    }

    public JBExp(JConj conj1) {
        this.conj1 = conj1;
        print = conj1.toString();
    }

    public JBExp(JAtom atom1, JConj conj2) {
        this.atom1 = atom1;
        this.conj2 = conj2;
        print = atom1 + " OR " + conj2;
    }

    public JBExp(JAtom atom1, JAtom atom2) {
        this.atom1 = atom1;
        this.atom2 = atom2;
        print = atom1 + " OR " + atom2;
    }

    @Override
    public String toString() {
        return print;
    }
}
