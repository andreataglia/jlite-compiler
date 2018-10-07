package utils;

public class ClassNameType extends BasicType {
    public String name;

    public ClassNameType(String name) {
        super(DataType.OBJECT); this.name = name;
    }

}
