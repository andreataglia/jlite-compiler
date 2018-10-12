package jnodes;

import concrete_nodes.AssignmentStmt;
import concrete_nodes.Stmt;

// ident EQUAL exp SEMICOLON
// | functionId DOT ident EQUAL exp SEMICOLON
public class JAssignStmt extends JStmt {
    public JId id;
    public JExp exp;
    public JAtom atom;

    AssignmentStmt stmt;

    public JAssignStmt(JId id, JExp exp) {
        this.id = id;
        this.exp = exp;
        stmt = new AssignmentStmt(id.s, exp.getConcreteExpr());
    }

    public JAssignStmt(JAtom atom, JId id, JExp exp) {
        this.id = id;
        this.exp = exp;
        this.atom = atom;

        stmt = new AssignmentStmt(id.s, atom.getConcreteNode(), exp.getConcreteExpr());
    }

    @Override
    public String toString() {
        return (atom != null ? atom + "." : "") + id + "=" + exp + ";";
    }

    @Override
    Stmt getConcreteStmt() {
        return stmt;
    }
}
