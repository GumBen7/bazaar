package main.controller.operations.searchOperation.criterias.lastNameCriteria;

import main.controller.operations.searchOperation.criterias.Criteria;
import main.controller.operations.searchOperation.criterias.CriteriaFactory;

public class LastNameCriteriaFactory extends CriteriaFactory {
    @Override
    public Criteria getCriteria() {
        return new LastNameCriteria(super.getCriteriaCommandObject());
    }
}
