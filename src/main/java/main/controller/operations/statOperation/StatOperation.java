package main.controller.operations.statOperation;

import main.controller.Controller;
import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOString;
import main.controller.operations.Operation;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Vector;

public class StatOperation implements Operation {
    private static final String START_DATE_STR = "startDate";
    private static final String END_DATE_STR = "endDate";
    private static final String TOTAL_DAYS_STR = "totalDays";
    private static final String CUSTOMERS_STR = "customers";
    private static final String PURCHASES_STR = "purchases";
    private static final String TOTAL_EXPENSES_STR = "totalExpenses";
    private static final String AVG_EXPENSES_STR = "avgExpenses";
    private static final String NAME_STR = "name";
    private static final String EXPENSES_STR = "expenses";
    private static final String TOTAL_DAYS_QUERY =
            "SELECT " +
                    "count(*) " +
            "FROM " +
                    "calendar_dates " +
            "WHERE is_working_day = 'Y' " +
                    "AND calendar_day >= to_date(?, 'yyyy-mm-dd') " +
                    "AND calendar_day <= to_date(?, 'yyyy-mm-dd');";
    private static final String CUSTOMERS_QUERY =
            "SELECT * " +
            "FROM " +
                    "(SELECT " +
                        "customers.id, " +
                        "surname, " +
                        "customers.name, " +
                        "products.name, " +
                        "sum(cost) " +
                    "FROM " +
                        "purchases " +
                    "LEFT JOIN customers " +
                        "ON customers.id = purchases.customer_id " +
                    "LEFT JOIN products " +
                        "ON products.id = purchases.product_id " +
                    "LEFT JOIN calendar_dates " +
                        "ON date = calendar_day " +
                    "WHERE " +
                        "date >= to_date(?, 'yyyy-mm-dd') " +
                        "AND date <= to_date(?, 'yyyy-mm-dd') " +
                        "AND is_working_day = 'Y' " +
                    "GROUP BY " +
                        "customers.id, " +
                        "products.id " +
                    "ORDER BY " +
                        "sum(cost) desc) " +
                    "AS costs " +
            "ORDER BY id;";
    private String startDate = null;
    private String endDate = null;
    private Vector<Double> customersExpenses;

    @Override
    public IOObject operate(IOObject input) {
        IOObject result = new IOObject();
        setDates(input);
        if (startDate != null && endDate != null) {
            Long totalDays = getTotalDays();
            if (totalDays != null) {
                result.put(TOTAL_DAYS_STR, totalDays);
                customersExpenses = new Vector<>();
                result.put(CUSTOMERS_STR, getCustomers());
                result.put(TOTAL_EXPENSES_STR, sumExpenses());
                result.put(AVG_EXPENSES_STR, avgExpenses());
            }
        }
        return result;
    }

    private void setDates(IOObject input) {
        if (input.get(START_DATE_STR) instanceof IOString) {
            startDate = ((IOString) input.get(START_DATE_STR)).getValue();
        }
        if (input.get(END_DATE_STR) instanceof  IOString) {
            endDate = ((IOString) input.get(END_DATE_STR)).getValue();
        }
    }

    private Long getTotalDays() {
        String[] args = {startDate, endDate};
        ResultSet rs = Controller.executeQuery(TOTAL_DAYS_QUERY, args);
        try {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private IOArray getCustomers() {
        IOArray array = new IOArray();
        String[] args = {startDate, endDate};
        ResultSet rs = Controller.executeQuery(CUSTOMERS_QUERY, args);
        int id = -1;
        IOObject customer = null;
        double totalExpenses = 0.;
        try {
            while (rs.next()) {
                if (id != rs.getInt(1)) {
                    if (customer != null) {
                        customer.put(TOTAL_EXPENSES_STR, totalExpenses);
                        customersExpenses.add(totalExpenses);
                        array.add(customer);
                    }
                    id = rs.getInt(1);
                    customer = new IOObject();
                    String name = rs.getString(2) + " " + rs.getString(3);
                    customer.put(NAME_STR, name);
                    IOArray purchases = new IOArray();
                    IOObject firstProduct = new IOObject();
                    firstProduct.put(NAME_STR, rs.getString(4));
                    firstProduct.put(EXPENSES_STR, rs.getDouble(5));
                    purchases.add(firstProduct);
                    customer.put(PURCHASES_STR, purchases);
                    totalExpenses = rs.getDouble(5);
                } else {
                    IOObject nextProduct = new IOObject();
                    nextProduct.put(NAME_STR, rs.getString(4));
                    nextProduct.put(EXPENSES_STR, rs.getDouble(5));
                    ((IOArray)customer.get(PURCHASES_STR)).add(nextProduct);
                    totalExpenses += rs.getDouble(5);
                }
            }
            if (customer != null) {
                customer.put(TOTAL_EXPENSES_STR, totalExpenses);
                customersExpenses.add(totalExpenses);
                array.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }

    private double sumExpenses() {
        double sum = 0.;
        for (Double d : customersExpenses) {
            sum += d;
        }
        return sum;
    }

    private double avgExpenses() {
        if (customersExpenses.size() == 0) {
            return 0.;
        }
        DecimalFormatSymbols dFS = new DecimalFormatSymbols();
        dFS.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.##", dFS);
        df.setRoundingMode(RoundingMode.HALF_UP);
        String s = df.format(sumExpenses() / customersExpenses.size());
        return Double.parseDouble(s);
    }
}
