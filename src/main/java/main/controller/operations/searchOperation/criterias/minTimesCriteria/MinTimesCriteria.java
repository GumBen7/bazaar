package main.controller.operations.searchOperation.criterias.minTimesCriteria;

import main.controller.Controller;
import main.controller.io.entities.*;
import main.controller.operations.searchOperation.SearchOperation;
import main.controller.operations.searchOperation.criterias.Criteria;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MinTimesCriteria extends Criteria {
    public static final String CRITERIA_NAME = "minTimes";
    private static final String PRODUCT_NAME_STR = SearchOperation.PRODUCT_NAME_STR;
    private static final String QUERY =
            "SELECT " +
                    "surname, " +
                    "customers.name " +
            "FROM " +
                    "customers " +
            "LEFT JOIN purchases " +
                    "ON customers.id = purchases.customer_id " +
            "LEFT JOIN products " +
                    "ON purchases.product_id = products.id " +
                    "AND products.name = ? " +
            "GROUP BY " +
                    "customers.id " +
            "HAVING " +
                    "count(products) >= ?;";

    public MinTimesCriteria(IOObject object) {
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
        if (this.get(CRITERIA_STR) instanceof IOObject) {
            IOObject criteriaNameObject = (IOObject) this.get(CRITERIA_STR);
            String productForSearch = getProductForSearch(criteriaNameObject);
            Long minTimesForSearch = getMinTimesForSearch(criteriaNameObject);
            if (productForSearch != null && minTimesForSearch != null) {
                ResultSet rs = Controller.executeQuery(QUERY, productForSearch, minTimesForSearch);
                try {
                    while (rs.next()) {
                        IOObject customer = getCustomerFromResultSet(rs);
                        array.add(customer);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return array;
    }

    private String getProductForSearch(IOObject criteriaNameObject) {
        if (criteriaNameObject.get(PRODUCT_NAME_STR) instanceof IOString) {
            return ((IOString) criteriaNameObject.get(PRODUCT_NAME_STR)).getValue();
        }
        return null;
    }

    private Long getMinTimesForSearch(IOObject criteriaNameObject) {
        if (criteriaNameObject.get(CRITERIA_NAME) instanceof IONumber) {
            return ((IONumber) criteriaNameObject.get(CRITERIA_NAME)).longValue();
        }
        return null;
    }
}
