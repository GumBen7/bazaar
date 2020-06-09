package main.controller.operations.searchOperation.criterias.badCustomerCriteria;

import main.controller.operations.searchOperation.criterias.Criteria;
import main.controller.operations.searchOperation.criterias.CriteriaFactory;

public class BadCustomersCriteriaFactory extends CriteriaFactory {
    @Override
    public Criteria getCriteria() {
        return new BadCustomersCriteria(super.getCriteriaCommandObject());
    }
}
