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
        return (Integer) value;
    }

    @Override
    public long longValue() {
        return (Long) value;
    }

    @Override
    public float floatValue() {
        return (Float) value;
    }

    @Override
    public double doubleValue() {
        return (Double) value;
    }
}
