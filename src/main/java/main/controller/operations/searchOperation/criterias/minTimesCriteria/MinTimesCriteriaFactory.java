package main.controller.operations.searchOperation.criterias.minTimesCriteria;

import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.criterias.Criteria;
import main.controller.operations.searchOperation.criterias.CriteriaFactory;

import java.util.*;

public class MinTimesCriteriaFactory extends CriteriaFactory {
    @Override
    public Criteria getCriteria() {
        List<Map.Entry<String, IOValue>> entries = new ArrayList<>(super.getCriteriaCommandObject().entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, IOValue>>() {
            @Override
            public int compare(Map.Entry<String, IOValue> o1, Map.Entry<String, IOValue> o2) {
                if (o2.getKey().equals(MinTimesCriteria.CRITERIA_NAME)) {
                    return -1;
                }
                return 0;
            }
        });
        IOObject sorted = new IOObject();
        for (Map.Entry<String, IOValue> entry : entries) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return new MinTimesCriteria(sorted);
    }
}
