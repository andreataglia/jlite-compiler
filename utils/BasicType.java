package utils;

public class BasicType extends DataType {
    public DataType dataType;

    public BasicType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        String ret = dataType.name().toLowerCase();
        if (dataType == DataType.OBJECT) {
            ret = ((ClassNameType) this).name;
        }
        return ret;
    }

    public enum DataType {
        INT,
        STRING,
        BOOL,
        VOID,
        OBJECT,
        NULL
    }

    public boolean equals(Object other) {
        if (other instanceof BasicType){
            return this.toString().equals(other.toString());
        }
        return false;
    }

    public boolean equals(BasicType other) {
        return this.toString().equals(other.toString());
    }

    public boolean equals(BasicType.DataType dataType) {
        return this.dataType.name().equals(dataType.name());
    }
}
