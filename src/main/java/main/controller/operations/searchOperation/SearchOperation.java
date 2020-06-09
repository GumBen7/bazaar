package main.controller.operations.searchOperation;

import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.Operation;
import main.controller.operations.searchOperation.criterias.Criteria;
import main.controller.operations.searchOperation.criterias.CriteriaFactory;
import main.controller.operations.searchOperation.criterias.CriteriaType;
import main.controller.operations.searchOperation.criterias.aggregCriteria.AggregCriteria;
import main.controller.operations.searchOperation.criterias.expensesCriteria.ExpensesCriteria;
import main.controller.operations.searchOperation.criterias.minTimesCriteria.MinTimesCriteria;

import java.util.ArrayList;
import java.util.List;

public class SearchOperation implements Operation {
    @Override
    public IOObject operate(IOObject input) {
        IOObject results = new IOObject();
        AggregCriteria criterias = getCriterias(input);
        results.put("results", criterias.apply());
        return results;
    }

    private AggregCriteria getCriterias(IOObject input) {
        List<Criteria> criterias = new ArrayList<>();
        if (input.get("criterias") instanceof IOArray) {
            for (IOValue criteriaCommand : (IOArray) input.get("criterias")) {
                if (criteriaCommand instanceof IOObject) {
                    IOObject criteriaCommandObject = (IOObject) criteriaCommand;
                    String criteriaName = "";
                    if (criteriaCommandObject.size() == 1) {
                        criteriaName = criteriaCommandObject.keySet().iterator().next();
                    } else if (criteriaCommandObject.size() == 2) {
                        if (criteriaCommandObject.containsKey(MinTimesCriteria.NAME)
                                && criteriaCommandObject.containsKey("productName")) {
                            criteriaName = MinTimesCriteria.NAME;
                        } else if (criteriaCommandObject.containsKey("minExpenses")
                                && criteriaCommandObject.containsKey("maxExpenses")) {
                            criteriaName = ExpensesCriteria.NAME;
                        }
                    }
                    CriteriaFactory factory = CriteriaFactory.factory(CriteriaType.valueOf(criteriaName.toUpperCase()), criteriaCommandObject);
                    Criteria criteria = factory.getCriteria();
                    criterias.add(criteria);
                }
            }
        }
        return new AggregCriteria(criterias);
    }
}
