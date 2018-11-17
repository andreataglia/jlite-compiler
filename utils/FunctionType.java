package utils;

import java.util.ArrayList;

public class FunctionType extends DataType {

    public ArrayList<BasicType> paramsType;
    public BasicType returnType;

    FunctionType(ArrayList<BasicType> paramsType, BasicType returnType) {
        this.paramsType = paramsType;
        this.returnType = returnType;
    }

    public boolean equals(FunctionType other) {
        boolean ret = true;
        if (other.returnType.equals(this)) ret = false;
        if (paramsType.size() == other.paramsType.size()) {
            int i = 0;
            for (BasicType b : paramsType) {
                if (!b.equals(other.paramsType.get(i))) ret = false;
                i++;
            }
        }
        return ret;
    }

    boolean paramsMatch(ArrayList<BasicType> params) {
        if (params.size() != paramsType.size()) return false;
        for (int i = 0; i < params.size(); i++) {
            if (!params.get(i).equals(paramsType.get(i))) return false;
        }
        return true;
    }

}
