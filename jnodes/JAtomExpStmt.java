package jnodes;

import concrete_nodes.FunctionCallStmt;
import concrete_nodes.Stmt;
import concrete_nodes.expressions.AtomFunctionCall;
import concrete_nodes.expressions.Expr;

// functionId LPAREN expList RPAREN SEMICOLON
public class JAtomExpStmt extends JStmt {
    public JAtom atom;
    public JExpList expList;

    public JAtomExpStmt(JAtom atom, JExpList expList) {
        this.atom = atom;
        this.expList = expList;
    }

    @Override
    public String toString() {
        return atom + "(" + expList + ");\n";
    }

    @Override
    Stmt getConcreteStmt() {
        System.err.println(">>>>>>>>>>> New AtomFunctionCall");
        for (Expr e : expList.getExprList()) {
            System.err.println(">>>>" + e);
        }
        return new FunctionCallStmt(new AtomFunctionCall(atom.getConcreteNode(), expList.getExprList()));
    }
}
