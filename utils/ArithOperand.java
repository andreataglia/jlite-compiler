package utils;

public enum ArithOperand {
        PLUS("+"),
        MINUS("-"),
        TIME("*"),
        DIVIDE("/");

    String symbol;

    ArithOperand(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static ArithOperand fromString(String n){
        switch (n){
            case "+": return ArithOperand.PLUS;
            case "-": return ArithOperand.MINUS;
            case "*": return ArithOperand.TIME;
            case "/": return ArithOperand.DIVIDE;
        }
        return null;
    }
}
