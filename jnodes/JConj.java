package jnodes;


// conj ::= conj AND rExp
//          | rExp
//          | conj AND atom
//          | atom AND atom
//          | atom AND rExp
public class JConj extends JNode {
    //left side
    public JConj conj1;
    public JRExp rexp;
    public JAtom atom1;

    //right side
    public JRExp rexp2;
    public JAtom atom2;

    String print;

    public JConj(JAtom atom1, JRExp rexp2) {
        this.atom1 = atom1;
        this.rexp2 = rexp2;
        print = atom1 + " AND " + rexp2;
    }

    public JConj(JAtom atom1, JAtom atom2) {
        this.atom1 = atom1;
        this.atom2 = atom2;
        print = atom1 + " AND " + atom2;
    }

    public JConj(JRExp rexp) {
        this.rexp = rexp;
        print = rexp.toString();
    }

    public JConj(JConj conj1, JAtom atom2) {
        this.conj1 = conj1;
        this.atom2 = atom2;
        print = conj1 + " AND " + atom2;
    }

    public JConj(JConj conj1, JRExp rexp2) {
        this.conj1 = conj1;
        this.rexp2 = rexp2;
        print = conj1 + " AND " + rexp2;
    }

    @Override
    public String toString() {
        return print;
    }

}
