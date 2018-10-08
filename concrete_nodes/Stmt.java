package concrete_nodes;

import utils.Visitor;

public abstract class Stmt extends Node{
    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
