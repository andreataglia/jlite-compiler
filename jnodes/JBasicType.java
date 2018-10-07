package jnodes;

import utils.BasicType;

public class JBasicType extends JNode {

    public BasicType basicType;

    public JBasicType(BasicType basicType) {
        this.basicType = basicType;
    }

    @Override
    public String toString() {
        return basicType.toString();
    }
}
