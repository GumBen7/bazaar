package main.controller.operations.searchOperation.criterias.minTimesCriteria;

import main.controller.operations.searchOperation.criterias.Criteria;
import main.controller.operations.searchOperation.criterias.CriteriaFactory;

public class MinTimesCriteriaFactory extends CriteriaFactory {
    @Override
    public Criteria getCriteria() {
        return new MinTimesCriteria(super.getCriteriaCommandObject());
    }
}
