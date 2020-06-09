package main.controller.operations.searchOperation;

import main.controller.io.entities.IOObject;
import main.controller.operations.Operation;

public class SearchOperation implements Operation {
    @Override
    public IOObject operate(IOObject input) {
        IOObject result = new IOObject();
        return input;
    }
}
