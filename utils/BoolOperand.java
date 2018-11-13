package utils;

public enum BoolOperand {
    OR("||"),
    AND("&&");

    String symbol;

    BoolOperand(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public boolean equals(BoolOperand other){
        return this.name().equals(other.name());
    }
}
