package main.controller.operations.searchOperation.criterias.expensesCriteria;

import main.controller.Controller;
import main.controller.io.entities.IOArray;
import main.controller.io.entities.IONumber;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOValue;
import main.controller.operations.searchOperation.SearchOperation;
import main.controller.operations.searchOperation.criterias.Criteria;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpensesCriteria extends Criteria {
    public static final String CRITERIA_NAME = "expenses";
    private static final String MIN_EXPENSES_STR = SearchOperation.MIN_EXPENSES_STR;
    private static final String MAX_EXPENSES_STR = SearchOperation.MAX_EXPENSES_STR;
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
            "GROUP BY " +
                    "customers.id " +
            "HAVING " +
                    "coalesce(sum(cost), 0) BETWEEN ? AND ?;";

    public ExpensesCriteria(IOObject object) {
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
        if (this.get(CRITERIA_STR) instanceof IOObject) {
            IOObject criteriaNameObject = (IOObject) this.get(CRITERIA_STR);
            Double minExpensesForSearch = getMinExpensesForSearch(criteriaNameObject);
            Double maxExpensesForSearch = getMaxExpensesForSearch(criteriaNameObject);
            if (minExpensesForSearch != null && maxExpensesForSearch != null) {
                ResultSet rs = Controller.executeQuery(QUERY, minExpensesForSearch, maxExpensesForSearch);
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

    private Double getMinExpensesForSearch(IOObject criteriaNameObject) {
        if (criteriaNameObject.get(MIN_EXPENSES_STR) instanceof IONumber) {
            return ((IONumber) criteriaNameObject.get(MIN_EXPENSES_STR)).doubleValue();
        }
        return null;
    }

    private Double getMaxExpensesForSearch(IOObject criteriaNameObject) {
        if (criteriaNameObject.get(MAX_EXPENSES_STR) instanceof IONumber) {
            return ((IONumber) criteriaNameObject.get(MAX_EXPENSES_STR)).doubleValue();
        }
        return null;
    }
}
