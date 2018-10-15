package concrete_nodes.expressions;

import utils.Visitor;

public class AtomGrd extends Atom{
    //<id> | <this> | <null>
    public String id;

    public AtomGrd(String id) {
        this.id = id;
    }

    public boolean isNullGround(){
        return "null".equalsIgnoreCase(id);
    }

    public boolean isThisGround(){
        return "this".equalsIgnoreCase(id);
    }

    public boolean isIdentifierGround(){
        return (!isThisGround() && !isNullGround());
    }


    @Override
    public Object accept(Visitor visitor)throws Exception {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return id;
    }
}
