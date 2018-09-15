package jnodes;

//varDecl ::= type ident SEMICOLON
public class JVarDecl extends JNode{
    public JType type;
    public JId id;

    public JVarDecl(JType type, JId id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return type + " " + id + ";\n";
    }
}
