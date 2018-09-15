package jnodes;

public class JMinusFtr extends JNode {
    public JFtr ftr;

    public JMinusFtr(JFtr ftr){
        this.ftr = ftr;
    }

    @Override
    public String toString() {
        return "- " + ftr;
    }
}
