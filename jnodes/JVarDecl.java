package jnodes;

//varDecl ::= type ident SEMICOLON
public class JVarDecl extends JNode{
    public JBasicType type;
    public JId id;

    public JVarDecl(JBasicType type, JId id) {
        this.type = type;
        this.id = id;
        System.out.println(type + " " + id + " declared");
    }

    @Override
    public String toString() {
        return type + " " + id + ";\n";
    }
}
