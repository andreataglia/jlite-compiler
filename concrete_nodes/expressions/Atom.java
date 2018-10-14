package concrete_nodes.expressions;

import concrete_nodes.Node;
import utils.BasicType;
import utils.Visitor;

public abstract class Atom extends Node {
    public BasicType type;

    @Override
    public abstract String toString();
}
