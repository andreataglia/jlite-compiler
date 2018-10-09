package utils;

public class VarDecl {
    String id;
    BasicType type;

    public VarDecl(String id, BasicType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof VarDecl){
            VarDecl element = (VarDecl) obj;
            if(element != null && this.id.equals(element.id)){
                return true;
            }
        }
        return false;
    }
}
