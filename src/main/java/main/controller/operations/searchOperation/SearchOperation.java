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
    public static final String RESULTS_STR = "results";
    private static final String CRITERIAS_STR = "criterias";
    private static final String PRODUCT_NAME_STR = "productName";
    private static final String MIN_EXPENSES_STR = "minExpenses";
    private static final String MAX_EXPENSES_STR = "maxExpenses";

    @Override
    public IOObject operate(IOObject input) {
        IOObject results = new IOObject();
        AggregCriteria criterias = getCriterias(input);

        results.put(RESULTS_STR, criterias.apply());
        return results;
    }

    private AggregCriteria getCriterias(IOObject input) {
        List<Criteria> criterias = new ArrayList<>();
        if (input.get(CRITERIAS_STR) instanceof IOArray) {
            for (IOValue criteriaCommand : (IOArray) input.get(CRITERIAS_STR)) {
                if (criteriaCommand instanceof IOObject) {
                    IOObject criteriaCommandObject = (IOObject) criteriaCommand;
                    Criteria criteria = getCriteriaByCommand(criteriaCommandObject);
                    criterias.add(criteria);
                }
            }
        }
        return new AggregCriteria(criterias);
    }

    private Criteria getCriteriaByCommand(IOObject criteriaCommandObject) {
        String criteriaName = "";
        if (criteriaCommandObject.size() == 1) {
            criteriaName = criteriaCommandObject.keySet().iterator().next();
        } else if (criteriaCommandObject.size() == 2) {
            if (criteriaCommandObject.containsKey(MinTimesCriteria.NAME)
                    && criteriaCommandObject.containsKey(PRODUCT_NAME_STR)) {
                criteriaName = MinTimesCriteria.NAME;
            } else if (criteriaCommandObject.containsKey(MIN_EXPENSES_STR)
                    && criteriaCommandObject.containsKey(MAX_EXPENSES_STR)) {
                criteriaName = ExpensesCriteria.NAME;
            }
        }
        CriteriaFactory factory = CriteriaFactory.factory(CriteriaType.valueOf(criteriaName.toUpperCase()), criteriaCommandObject);
        return factory.getCriteria();
    }
}
