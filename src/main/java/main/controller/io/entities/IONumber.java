package main.controller.io.entities;

public class IONumber implements IOValue {
    private final Long value;

    public IONumber(Long v) {
        value = v;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
