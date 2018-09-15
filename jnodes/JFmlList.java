package jnodes;

//fmlList ::=
//          | type ident fmlRestList
public class JFmlList extends JNode {
    public JType type;
    public JId id;
    public JFmlRestList jfmlRestList;

    public JFmlList(JType type, JId id, JFmlRestList jfmlRestList) {
        this.type = type;
        this.id = id;
        this.jfmlRestList = jfmlRestList;
    }

    public JFmlList() {
    }

    @Override
    public String toString() {
        return jfmlRestList == null ? "" : type + " " + id + " " + jfmlRestList;
    }
}
