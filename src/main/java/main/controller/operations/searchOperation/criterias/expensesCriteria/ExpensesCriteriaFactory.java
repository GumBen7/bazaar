package main.controller.operations.searchOperation.criterias.expensesCriteria;

import main.controller.operations.searchOperation.criterias.Criteria;
import main.controller.operations.searchOperation.criterias.CriteriaFactory;

public class ExpensesCriteriaFactory extends CriteriaFactory {
    @Override
    public Criteria getCriteria() {
        return new ExpensesCriteria(super.getCriteriaCommandObject());
    }
}
