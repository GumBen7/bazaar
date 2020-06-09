package main.controller;

import database.PostgreSQLDatabase;
import main.controller.io.JSONWorker;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOString;
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
            IOObject output = getResultObject(opTypeArg, input);
            JSONWorker.writeJson(outputFileArg, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IOObject getResultObject(String opTypeArg, IOObject input) {
        IOObject output = new IOObject();
        output.put("type", new IOString(opTypeArg));
        Operation operation = getOperationByCommand(opTypeArg);
        IOObject opResult = operation.operate(input);
        //for (opResult.)
        return opResult;
    }

    private Operation getOperationByCommand(String opTypeArg) {
        OperationType type = OperationType.valueOf(opTypeArg.toUpperCase());
        OperationFactory factory = OperationFactory.factory(type);
        return factory.getOperation();
    }

}
