package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String initials;
    private String name;
    private List<Activity> assignedActivities;

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public Employee(String initials) {
        this.initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
    }

    public Employee(String initials, String name, List<Activity> assignedActivities) {
        this.initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
        this.name = name;
        this.assignedActivities = assignedActivities != null ? assignedActivities : new ArrayList<>();
    }

    // LOGIC
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public void assignActivity(Activity activity) {
        if (!assignedActivities.contains(activity)) {
            assignedActivities.add(activity);
        }
    }

    // Getters
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public String getInitials() {
        return initials;
    }

    public String getName() {
        return name;
    }

    public List<Activity> getAssignedActivities() {
        return assignedActivities;
    }

    // Setters
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public void setInitials(String initials) {
        this.initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
    }

}
