package jnodes;

// expRestList ::=
//              | expRest expRestList
public class JExpRestList extends JNode {
    public JExpRest expRest;
    public JExpRestList expRestList;

    public JExpRestList(JExpRest expRest, JExpRestList expRestList) {
        this.expRest = expRest;
        this.expRestList = expRestList;
    }

    public JExpRestList() {
    }

    @Override
    public String toString() {
        return expRest != null ? expRest + " " + expRestList : "";
    }
}
