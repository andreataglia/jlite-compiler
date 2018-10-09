package concrete_nodes.expressions;

import concrete_nodes.Node;
import utils.Visitor;

public abstract class Atom extends Node {
    @Override
    public abstract String toString();
}
