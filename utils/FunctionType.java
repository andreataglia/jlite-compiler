package utils;

import java.util.ArrayList;

public class FunctionType extends DataType {

    public ArrayList<BasicType> paramsType;
    public BasicType returnType;

    public FunctionType(ArrayList<BasicType> paramsType, BasicType returnType) {
        this.paramsType = paramsType;
        this.returnType = returnType;
    }
}
