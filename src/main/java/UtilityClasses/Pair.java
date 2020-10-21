package UtilityClasses;

public class Pair {
    private boolean key;
    private Goods value;
    public Pair(boolean key, Goods value) {
        this.key = key;
        this.value = value;
    }
    public boolean getKey() {
        return key;
    }
    public Goods getValue() {
        return value;
    }
}
