package jnodes;

//varDecl ::= expType ident SEMICOLON
public class JVarDecl extends JNode{
    public JBasicType type;
    public JId id;

    public JVarDecl(JBasicType type, JId id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return type + " " + id + ";\n";
    }
}
