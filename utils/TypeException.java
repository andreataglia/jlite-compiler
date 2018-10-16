package utils;

public class TypeException extends Exception {

    public TypeException(String issue) {
        super(issue);
    }

    public TypeException(String issue, String className) {
        this(issue + " in Class:" + className);
    }

    public TypeException(String issue, String inClass, String inMethod) {
        this(issue + " - in Method:" + inMethod , inClass);
    }
}
