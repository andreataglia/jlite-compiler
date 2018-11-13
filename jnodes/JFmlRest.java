package jnodes;

//fmlRest ::= COMMA expType ident
public class JFmlRest extends JNode{
    public JBasicType type;
    public JId id;

    public JFmlRest(JBasicType type, JId id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return ", " + type + " " + id;
    }
}
