package main.controller.operations.searchOperation.criterias.aggregCriteria;

import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.criterias.Criteria;

import java.util.ArrayList;
import java.util.List;

public class AggregCriteria extends Criteria {
    private ArrayList<Criteria> criterias;

    public AggregCriteria(List<Criteria> cs) {
        criterias = new ArrayList<>(cs);
    }

    @Override
    public IOValue apply() {
        IOArray array = new IOArray();
        for (Criteria criteria : criterias) {
            array.add(criteria.apply());
        }
        return array;
    }
}
