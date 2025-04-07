package model;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    String activityId;
    String activityName;
    int budgetedHours;
    int startWeek;
    int endWeek;
    List<Employee> employees;

    public Activity(String activityId, String activityName, int budgetedHours, int startWeek, int endWeek) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.budgetedHours = budgetedHours;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.employees = new ArrayList<>();
    }

    public void assignEmployee(Employee employee) {
        employees.add(employee);
    }
}
