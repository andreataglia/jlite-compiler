package nodes3;

import utils.BasicType;

public class Type3 {
    public BasicType type;

    public Type3(BasicType type) {
        if (type.equals(BasicType.DataType.NULL)) System.err.println("WARNING: initialized a NULL Type3");
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
