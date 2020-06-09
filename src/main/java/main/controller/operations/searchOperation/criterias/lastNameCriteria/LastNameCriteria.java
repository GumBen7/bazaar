package main.controller.operations.searchOperation.criterias.lastNameCriteria;

import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.criterias.Criteria;

public class LastNameCriteria extends Criteria {
    public final static String NAME = "lastName";

    public LastNameCriteria(IOObject object) {
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