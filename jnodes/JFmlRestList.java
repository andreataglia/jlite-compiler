package jnodes;

// '' | fmlRest fmlRestList
public class JFmlRestList extends JNode {

    public JFmlRest fmlRest;
    public JFmlRestList fmlRestList;

    public JFmlRestList(JFmlRest fmlRest, JFmlRestList fmlRestList) {
        this.fmlRest = fmlRest;
        this.fmlRestList = fmlRestList;
    }

    public JFmlRestList() {
    }

    @Override
    public String toString() {
        return fmlRest == null ? "" : fmlRest + "" + fmlRestList;
    }
}
