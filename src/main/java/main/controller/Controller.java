package main.controller;

import database.PostgreSQLDatabase;
import main.controller.io.JSONWorker;
import main.controller.io.entities.IOObject;
import main.controller.operations.Operation;
import main.controller.operations.OperationFactory;
import main.controller.operations.OperationType;

public class Controller {
    public void dataBaseVersion() {
        PostgreSQLDatabase.version();
    }

    public void execute(String[] args) {
        try {
            final String opTypeArg = args[ArgSerial.OPER_TYPE.serial];
            final String inputFileArg = args[ArgSerial.INPUT.serial];
            final String outputFileArg = args[ArgSerial.OUTPUT.serial];

            IOObject input = JSONWorker.readJson(inputFileArg);
            OperationType type = OperationType.valueOf(opTypeArg.toUpperCase());
            OperationFactory factory = OperationFactory.factory(type);
            Operation operation = factory.getOperation();
            IOObject result = operation.operate(input);
            JSONWorker.writeJson(outputFileArg, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
