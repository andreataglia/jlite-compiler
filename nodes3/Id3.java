package nodes3;

public class Id3 extends Idc3 {
    String id;

    public Id3(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
