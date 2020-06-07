package main.controller.operations;

import main.controller.io.entities.IOObject;

public interface Operation {
    IOObject operate(IOObject input);
}
