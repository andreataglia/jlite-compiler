package jnodes;

//fmlRest ::= COMMA type ident
public class JFmlRest extends JNode{
    public JType type;
    public JId id;

    public JFmlRest(JType type, JId id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return ", " + type + " " + id;
    }
}
