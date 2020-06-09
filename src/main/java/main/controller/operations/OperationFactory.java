package main.controller.operations;

import main.controller.operations.searchOperation.SearchOperationFactory;
import main.controller.operations.statOperation.StatOperationFactory;

public abstract class OperationFactory {
    public static OperationFactory factory(OperationType type) {
        switch (type) {
            case SEARCH:
                return new SearchOperationFactory();
            case STAT:
                return new StatOperationFactory();
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract Operation getOperation();
}
