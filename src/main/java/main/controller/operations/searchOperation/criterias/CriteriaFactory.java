package main.controller.operations.searchOperation.criterias;

import main.controller.io.entities.IOObject;
import main.controller.operations.searchOperation.criterias.badCustomerCriteria.BadCustomersCriteriaFactory;
import main.controller.operations.searchOperation.criterias.expensesCriteria.ExpensesCriteriaFactory;
import main.controller.operations.searchOperation.criterias.lastNameCriteria.LastNameCriteriaFactory;
import main.controller.operations.searchOperation.criterias.minTimesCriteria.MinTimesCriteriaFactory;

public abstract class CriteriaFactory {
    private static IOObject criteriaCommandObject;

    public static CriteriaFactory factory(CriteriaType type, IOObject cr) {
        criteriaCommandObject = cr;
        switch (type) {
            case LASTNAME:
                return new LastNameCriteriaFactory();
            case MINTIMES:
                return new MinTimesCriteriaFactory();
            case EXPENSES:
                return new ExpensesCriteriaFactory();
            case BADCUSTOMERS:
                return new BadCustomersCriteriaFactory();
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract Criteria getCriteria();

    public IOObject getCriteriaCommandObject() {
        return criteriaCommandObject;
    }
}
