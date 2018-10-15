package nodes3;

import utils.BasicType;
import utils.ClassNameType;

public class CName3 extends Type3 {
    public ClassNameType name;

    public CName3(ClassNameType name) {
        super(new BasicType(BasicType.DataType.OBJECT));
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
