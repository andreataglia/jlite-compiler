package utils;

public class BasicType extends DataType {
    public DataType dataType;

    public BasicType(DataType dataType){
        this.dataType = dataType;
    }

    public enum DataType {
        INT,
        STRING,
        BOOL,
        VOID,
        OBJECT
    }
}
