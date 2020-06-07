package main.controller.io.entities;

public class IOString implements IOValue {
    private final String value;

    public IOString(String v) {
        value = v;
    }

    @Override
    public String toString() {
        return value;
    }
}
