package concrete_nodes.expressions;

import concrete_nodes.Node;
import utils.Visitor;

public abstract class Expr extends Node {
    @Override
    public abstract String toString();
}
