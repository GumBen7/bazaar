package main;

import main.controller.Controller;

public class App {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.operate(args);
    }
}
