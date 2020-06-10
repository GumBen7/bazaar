package main.controller.io.entities;

public class IONumber extends Number implements IOValue {
    private final Number value;

    public IONumber(Number v) {
        value = v;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }
}
