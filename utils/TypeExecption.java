package utils;

public class TypeExecption extends Exception {
    public String description;

    public TypeExecption(String s, String description) {
        super(s);
        this.description = description;
    }
}
