package main.controller.operations.searchOperation;

import main.controller.operations.Operation;
import main.controller.operations.OperationFactory;

public class SearchOperationFactory extends OperationFactory {
    @Override
    public Operation getOperation() {
        return new SearchOperation();
    }
}
