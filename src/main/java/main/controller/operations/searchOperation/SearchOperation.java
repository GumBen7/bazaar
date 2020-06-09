package main.controller.operations.searchOperation;

import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.Operation;
import main.controller.operations.searchOperation.criterias.Criteria;
import main.controller.operations.searchOperation.criterias.aggregCriteria.AggregCriteria;
import main.controller.operations.searchOperation.criterias.badCustomerCriteria.BadCustomersCriteria;
import main.controller.operations.searchOperation.criterias.expensesCriteria.ExpensesCriteria;
import main.controller.operations.searchOperation.criterias.lastNameCriteria.LastNameCriteria;
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
            for (IOValue criteria : (IOArray) input.get("criterias")) {
                if (criteria instanceof IOObject) {
                    if (((IOObject) criteria).containsKey(LastNameCriteria.NAME)) {
                        criterias.add(new LastNameCriteria((IOObject) criteria));
                    } else if (((IOObject) criteria).containsKey(MinTimesCriteria.NAME)) {
                        criterias.add(new MinTimesCriteria((IOObject) criteria));
                    } else if (((IOObject) criteria).containsKey(ExpensesCriteria.NAME)) {
                        criterias.add(new ExpensesCriteria((IOObject) criteria));
                    } else if (((IOObject) criteria).containsKey(BadCustomersCriteria.NAME)) {
                        criterias.add(new BadCustomersCriteria((IOObject) criteria));
                    }
                }
            }
        }
        return new AggregCriteria(criterias);
    }
}
