package main.controller.operations.searchOperation.criterias;

import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.SearchOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Criteria extends IOObject {
    protected static final String CRITERIA_STR = "criteria";
    protected static final String RESULTS_STR = SearchOperation.RESULTS_STR;
    protected static final String LAST_NAME_STR = "lastName";
    private static final String FIRST_NAME_STR = "firstName";

    public Criteria() {
    }

    public Criteria(IOObject object) {
        this.put(CRITERIA_STR, object);
    }

    public abstract IOValue apply();

    protected IOObject getCustomerFromResultSet(ResultSet rs) throws SQLException {
        IOObject customer = new IOObject();
        customer.put(LAST_NAME_STR, rs.getString(1));
        customer.put(FIRST_NAME_STR, rs.getString(2));
        return customer;
    }
}
