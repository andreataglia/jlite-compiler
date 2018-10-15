package jnodes;

import utils.BasicType;
import utils.ClassNameType;

public class JBasicType extends JNode {

    public BasicType basicType;

    public JBasicType(BasicType basicType) {
        this.basicType = basicType;
    }

    @Override
    public String toString() {
        String s = basicType.dataType.name();
        if (basicType.dataType == BasicType.DataType.OBJECT){
            s = ((ClassNameType) basicType).name;
        }
        return s;
    }
}
