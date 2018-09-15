package jnodes;

public class JType extends JNode {
    public String s;

    public JType(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
