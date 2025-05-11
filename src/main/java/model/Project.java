/**
 *
 *
 * @author Frederik, Lasse and Kim
 * Getters and Setters by Frederik and Lasse
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectID;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Activity> activityList;
    private Employee projectLeader;

    //Written by Lasse and Kim
    public Project(String projectID, String projectName, LocalDate startDate, LocalDate endDate) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityList = new ArrayList<>();
    }

    // Written by Frederik
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    //Written by Lasse
    public void assignProjectLeader(Employee leader) {
        this.projectLeader = leader;
    }

    // Getters
    public String getProjectID() {
        return projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate()   { return endDate;   }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

    // Setters

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setStartDate(LocalDate d) { this.startDate = d; }

    public void setEndDate(LocalDate d)   { this.endDate   = d; }


    public void setProjectLeader(Employee projectLeader) {
        this.projectLeader = projectLeader;
    }
}
