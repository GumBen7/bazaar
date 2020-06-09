package main.controller.operations.statOperation;

import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.operations.Operation;

public class StatOperation implements Operation {
    @Override
    public IOObject operate(IOObject input) {
        IOObject result = new IOObject();
        result.put("totalDays", 9);
        result.put("customers", new IOArray());
        return result;
    }
}
