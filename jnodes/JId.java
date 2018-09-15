package jnodes;

public class JId extends JNode {
    public String s;

    public JId(String s){
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
