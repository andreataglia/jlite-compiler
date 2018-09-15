package jnodes;

//expList ::= exp expRestList | ''
public class JExpList extends JNode{
    public JExp exp;
    public JExpRestList expRestList;

    public JExpList(JExp exp, JExpRestList expRestList) {
        this.exp = exp;
        this.expRestList = expRestList;
    }

    public JExpList(){

    }

    @Override
    public String toString() {
        return exp!=null? "" + exp + expRestList : "";
    }
}
