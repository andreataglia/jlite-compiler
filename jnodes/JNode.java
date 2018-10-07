package jnodes;

import utils.JVisitor;

abstract public class JNode {
    abstract public String toString();
    public void accept(JVisitor visitor){
        visitor.visit(this);
    }
}
