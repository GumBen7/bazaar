package main.controller.operations.searchOperation.criterias.badCustomerCriteria;

import main.controller.Controller;
import main.controller.io.entities.IOArray;
import main.controller.io.entities.IONumber;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.criterias.Criteria;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BadCustomersCriteria extends Criteria {
    public static final String CRITERIA_NAME = "badCustomers";
    private static final String QUERY =
            "SELECT " +
                    "surname, " +
                    "customers.name " +
            "FROM " +
                    "customers " +
            "LEFT JOIN purchases " +
                    "ON customers.id = purchases.customer_id " +
            "GROUP BY " +
                    "customers.id " +
            "ORDER BY " +
                    "count(purchases) " +
            "LIMIT ?;";

    public BadCustomersCriteria(IOObject object) {
        super(object);
    }

    @Override
    public IOValue apply() {
        IOObject object = new IOObject();
        object.putAll(this);
        object.put("results", getCustomers());
        return object;
    }

    private IOArray getCustomers() {
        IOArray array = new IOArray();
        Long countForSearch = getCountForSearch();
        if (countForSearch != null) {
            ResultSet rs = Controller.executeQuery(QUERY, countForSearch);
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

    private Long getCountForSearch() {
        if (this.get(CRITERIA_STR) instanceof  IOObject) {
            IOObject criteriaNameObject = (IOObject) this.get(CRITERIA_STR);
            if (criteriaNameObject.get(CRITERIA_NAME) instanceof IONumber) {
                return ((IONumber) criteriaNameObject.get(CRITERIA_NAME)).longValue();
            }
        }
        return null;
    }
}
