package utils;

public enum RelBoolOperand {
    LT("<"),
    GT(">"),
    LET("<="),
    GET(">="),
    EQUAL("=="),
    NOT_EQUAL("!=");

    String symbol;

    RelBoolOperand(String symbol) {
        this.symbol = symbol;
    }

    public static RelBoolOperand fromString(String s){
        switch (s){
            case "<": return RelBoolOperand.LT;
            case ">": return RelBoolOperand.GT;
            case "<=": return RelBoolOperand.LET;
            case ">=": return RelBoolOperand.GET;
            case "==": return RelBoolOperand.EQUAL;
            case "!=": return RelBoolOperand.NOT_EQUAL;
        }
        return null;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public boolean equals(RelBoolOperand other){
        return this.name().equals(other.name());
    }
}
