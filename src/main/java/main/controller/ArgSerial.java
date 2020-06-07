package main.controller;

public enum ArgSerial {
    OPER_TYPE(0), INPUT(1), OUTPUT(2);
    final int serial;
    ArgSerial(int s) {
        serial = s;
    }
}
