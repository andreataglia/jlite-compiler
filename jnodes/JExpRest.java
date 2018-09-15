package jnodes;

// expRest ::= COMMA exp
public class JExpRest extends JNode{
    public JExp exp;

    public JExpRest(JExp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return ", " + exp;
    }
}
