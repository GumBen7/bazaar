package main.controller;

import database.PostgreSQLDatabase;
import main.controller.io.JSONWorker;
import main.controller.io.entities.IOObject;
import main.controller.operations.Operation;
import main.controller.operations.OperationFactory;
import main.controller.operations.OperationType;

import java.sql.ResultSet;

public class Controller {
    private static final String TYPE_STR = "type";

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
        output.put(TYPE_STR, opTypeArg);
        Operation operation = getOperationByCommand(opTypeArg);
        IOObject opResult = operation.operate(input);
        output.putAll(opResult);
        return output;
    }

    private Operation getOperationByCommand(String opTypeArg) {
        OperationType type = OperationType.valueOf(opTypeArg.toUpperCase());
        OperationFactory factory = OperationFactory.factory(type);
        return factory.getOperation();
    }

    public static ResultSet executeQuery(String query, String[] args) {
        return PostgreSQLDatabase.execute(query, args);
    }
}
