package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    String initials;
    String name;
    List<Activity> assignedActivities;

    public Employee(String initials, String name, List<Activity> assignedActivities) {
        this.initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
        this.name = name;
        this.assignedActivities = assignedActivities;
    }

    public void logTime(Activity activity, double hours) {
    }

    public Employee getEmployee(String initials) {
        return new Employee(initials, name, assignedActivities);
    }
}
