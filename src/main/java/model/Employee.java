/**
 *
 *
 * @author Frederik and Kim
 * Getters and Setters by Frederik
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String initials;
    private String name;
    private List<Activity> assignedActivities;

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    //Written by Kim
    public Employee(String initials) {
        this.initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
    }

    //Written by Frederik
    public Employee(String initials, String name, List<Activity> assignedActivities) {
        this.initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
        this.name = name;
        this.assignedActivities = assignedActivities != null ? assignedActivities : new ArrayList<>();
    }

    // LOGIC
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    //Written by Frederik
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

    //Written by Frederik
    public void setInitials(String initials) {
        this.initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
    }

}
