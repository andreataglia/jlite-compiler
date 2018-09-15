package jnodes;


//bGrd ::= NOT bGrd | TRUE | FALSE
public class JBGrd extends JNode {
    public JBGrd jbGrd;
    public String bool;

    public JBGrd(String bool) {
        this.bool = bool;
    }

    public JBGrd(JBGrd jbGrd) {
        this.jbGrd = jbGrd;
    }

    @Override
    public String toString() {
        return jbGrd == null ? bool : "!" + jbGrd;
    }
}
