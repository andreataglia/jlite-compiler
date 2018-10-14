package nodes3;

//true | false | INTEGER_LITERAL | STRING_LITERAL | NULL
public class Const extends Idc3 {
    public Boolean booleanLiteral;
    public Integer integerLiteral;
    public String stringLiteral;
    public boolean nullLiteral = false;

    private String print;

    public Const(Boolean booleanLiteral) {
        this.booleanLiteral = booleanLiteral;
        print = String.valueOf(booleanLiteral);
    }

    public Const(Integer integerLiteral) {
        this.integerLiteral = integerLiteral;
        print = String.valueOf(integerLiteral);
    }

    public Const(String stringLiteral) {
        this.stringLiteral = stringLiteral;
        print = stringLiteral;
    }

    public Const() {
        nullLiteral = true;
        print = "null";
    }

    public boolean isBooleanLiteral() {
        return booleanLiteral;
    }

    public boolean isNullLiteral() {
        return nullLiteral;
    }

    public boolean isStringLiteral(){
        return stringLiteral != null;
    }

    public boolean isIntegerLiteral(){
        return integerLiteral != null;
    }

    @Override
    public String toString() {
        return print;
    }
}
