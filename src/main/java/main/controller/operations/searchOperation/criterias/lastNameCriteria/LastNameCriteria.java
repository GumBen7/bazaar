package main.controller.operations.searchOperation.criterias.lastNameCriteria;

import main.controller.Controller;
import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOString;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.criterias.Criteria;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LastNameCriteria extends Criteria {
    public final static String CRITERIA_NAME = LAST_NAME_STR;
    private static final String QUERY = "SELECT surname, name FROM customers WHERE surname = ?";

    public LastNameCriteria(IOObject object) {
        super(object);
    }

    @Override
    public IOValue apply() {
        IOObject object = new IOObject();
        object.putAll(this);
        object.put(RESULTS_STR, getCustomers());
        return object;
    }

    private IOArray getCustomers() {
        IOArray array = new IOArray();
        String lastNameForSearch = getLastNameForSearch();
        if (lastNameForSearch != null) {
            String[] args = {lastNameForSearch};
            ResultSet rs = Controller.executeQuery(QUERY, args);
            try {
                while (rs.next()) {
                    IOObject customer = getCustomerFromResultSet(rs);
                    array.add(customer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    private String getLastNameForSearch() {
        if (this.get(CRITERIA_STR) instanceof IOObject) {
            IOObject criteriaNameObject = (IOObject) this.get(CRITERIA_STR);
            if (criteriaNameObject.get(CRITERIA_NAME) instanceof IOString) {
                return ((IOString) criteriaNameObject.get(CRITERIA_NAME)).getValue();
            }
        }
        return null;
    }
}
