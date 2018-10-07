package jnodes;

// atom ::= atom DOT ident
//       | atom LPAREN expList RPAREN
//       | THIS
//       | ident
//       | NEW OBJECT LPAREN RPAREN
//       | LPAREN exp RPAREN
//       | NULL

public class JAtom extends JNode {
    public JAtom atom;
    public JId id;
    public JExpList expList;
    public JExp exp;
    public String s;
    public JClassNameType cname;

    String print;

    public JAtom(JClassNameType cname) {
        this.cname = cname;
        print = "new " + cname + "()";
    }

    public JAtom(JAtom atom, JId id) {
        this.atom = atom;
        this.id = id;
        print = atom + "." + id;
    }

    public JAtom(JAtom atom, JExpList expList) {
        this.atom = atom;
        this.expList = expList;
        print = atom + "(" + expList + ")";
    }

    public JAtom(JExp exp) {
        this.exp = exp;
        print = "(" + exp + ")";
    }

    public JAtom(JId id) {
        this.id = id;
        print = id.toString();
    }

    public JAtom(String s){
        this.s = s;
        print = s;
    }



    @Override
    public String toString() {
        return print;
    }
}
