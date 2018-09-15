package jnodes;


//         RETURN exp SEMICOLON
//       | RETURN SEMICOLON
public class JReturnStmt extends JStmt {
    public JExp exp;

    public JReturnStmt(JExp exp) {
        this.exp = exp;
    }

    public JReturnStmt() {
    }

    @Override
    public String toString() {
        return "return " + (exp != null ? exp : "") + ";\n";
    }
}
