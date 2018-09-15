package jnodes;

// atom DOT ident EQUAL exp SEMICOLON
public class JAtomStmt extends JStmt {
    public JAtom atom;
    public JId id;
    public JExp exp;

    public JAtomStmt(JAtom atom, JId id, JExp exp) {
        this.atom = atom;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return atom + "." + id + "=" + exp + ";\n";
    }
}
