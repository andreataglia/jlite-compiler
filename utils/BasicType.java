package utils;

public class BasicType extends DataType {
    public DataType dataType;

    public BasicType(DataType dataType){
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        String ret = dataType.name();
        if (dataType == DataType.OBJECT){
            ret = ((ClassNameType) this).name;
        }
        return ret;
    }

    public enum DataType {
        INT,
        STRING,
        BOOL,
        VOID,
        OBJECT
    }
}
