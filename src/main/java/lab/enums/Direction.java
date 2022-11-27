package lab.enums;

public enum Direction {
    LEFT,
    RIGHT,
    NONE;

    private static final Direction[] vals = values();

    public Direction next() {
        return vals[this.ordinal() + 1 % vals.length];
    }
}
