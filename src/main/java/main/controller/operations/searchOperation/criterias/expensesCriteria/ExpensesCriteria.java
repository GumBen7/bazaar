package main.controller.operations.searchOperation.criterias.expensesCriteria;

import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.criterias.Criteria;

public class ExpensesCriteria extends Criteria {
    public static final String NAME = "minExpenses";

    public ExpensesCriteria(IOObject object) {
        super(object);
    }

    @Override
    public IOValue apply() {
        IOObject object = new IOObject();
        object.putAll(this);
        object.put("results", new IOArray());
        return object;
    }
}
