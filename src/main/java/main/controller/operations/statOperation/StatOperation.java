package main.controller.operations.statOperation;

import main.controller.io.entities.IOObject;
import main.controller.operations.Operation;

public class StatOperation implements Operation {
    @Override
    public IOObject operate(IOObject input) {
        IOObject result = new IOObject();
        return input;
    }
}
