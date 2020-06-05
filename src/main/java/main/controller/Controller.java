package main.controller;

import database.PostgreSQLDatabase;
import main.controller.io.JSONWorker;
import main.controller.operations.Operation;
import main.controller.operations.OperationFactory;
import main.controller.operations.OperationType;
import org.json.simple.JSONObject;

public class Controller {
    public void dataBaseVersion() {
        PostgreSQLDatabase.version();
    }

    public void operate(String[] args) {
        try {
            JSONObject input = (JSONObject) JSONWorker.readJson(args[1]);
            OperationType type = OperationType.valueOf(args[0].toUpperCase());
            OperationFactory factory = OperationFactory.factory(type);
            Operation operation = factory.getOperation();
            JSONObject result = operation.operate(input);
            JSONWorker.writeJson(args[2], result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
