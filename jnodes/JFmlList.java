package jnodes;

//fmlList ::=
//          | type ident fmlRestList
public class JFmlList extends JNode {
    //first param
    public JBasicType type;
    public JId id;

    //rest of params
    public JFmlRestList jfmlRestList;

    public JFmlList(JBasicType type, JId id, JFmlRestList jfmlRestList) {
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
