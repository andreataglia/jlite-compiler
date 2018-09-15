package jnodes;

public class JSExp extends JExp {
    public String s;

    public JSExp(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
