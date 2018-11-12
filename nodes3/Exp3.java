package nodes3;

public abstract class Exp3 extends Node3 {
    public Type3 type;

    public Exp3(Type3 type) {
        this.type = type;
    }

    @Override
    public abstract String toString();

}
