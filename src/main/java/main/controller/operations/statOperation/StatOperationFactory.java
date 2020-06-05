package main.controller.operations.statOperation;

import main.controller.operations.Operation;
import main.controller.operations.OperationFactory;

public class StatOperationFactory extends OperationFactory {
    @Override
    public Operation getOperation() {
        return new StatOperation();
    }
}
