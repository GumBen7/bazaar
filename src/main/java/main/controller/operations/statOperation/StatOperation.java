package main.controller.operations.statOperation;

import main.controller.Controller;
import main.controller.io.entities.IOArray;
import main.controller.io.entities.IOObject;
import main.controller.io.entities.IOString;
import main.controller.operations.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatOperation implements Operation {
    private static final String START_DATE_STR = "startDate";
    private static final String END_DATE_STR = "endDate";
    private static final String TOTAL_DAYS_QUERY =
            "SELECT " +
                    "count(*) " +
            "FROM " +
                    "calendar_dates " +
            "WHERE is_working_day = 'Y' " +
                    "AND calendar_day >= to_date(?, 'yyyy-mm-dd') " +
                    "AND calendar_day <= to_date(?, 'yyyy-mm-dd');";
    private String startDate = null;
    private String endDate = null;

    @Override
    public IOObject operate(IOObject input) {
        IOObject result = new IOObject();
        setDates(input);
        if (startDate != null && endDate != null) {
            Long totalDays = getTotalDays();
            if (totalDays != null) {
                result.put("totalDays", totalDays);
                result.put("customers", new IOArray());
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
}
