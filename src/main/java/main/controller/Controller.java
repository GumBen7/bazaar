package main.controller;

import database.PostgreSQLDatabase;
import main.controller.io.JSONWorker;
import main.controller.operations.Operation;
import main.controller.operations.OperationFactory;
import main.controller.operations.OperationType;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Controller {
    public void dataBaseVersion() {
        PostgreSQLDatabase.version();
    }

    public void execute(String[] args) {
        try {
            final String opTypeArg = args[ArgSerial.OPER_TYPE.serial];
            final String inputFileArg = args[ArgSerial.INPUT.serial];
            final String outputFileArg = args[ArgSerial.OUTPUT.serial];
            JSONObject input =(JSONObject) JSONWorker.readJson(inputFileArg);
            OperationType type = OperationType.valueOf(opTypeArg.toUpperCase());
            OperationFactory factory = OperationFactory.factory(type);
            Operation operation = factory.getOperation();
            JSONObject result = operation.operate(input);
            JSONWorker.writeJson(outputFileArg, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
