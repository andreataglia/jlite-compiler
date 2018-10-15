package utils;

public class ClassNameType extends BasicType {
    public String name;

    public ClassNameType(String name) {
        super(DataType.OBJECT);
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof String) return name.equals(other);
        else if (other instanceof ClassNameType) return name.equals(((ClassNameType) other).name);
        return false;
    }
}
