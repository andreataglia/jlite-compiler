package concrete_nodes;

import utils.Visitor;

abstract public class Node {
    abstract public Object accept(Visitor visitor) throws Exception;
}
