package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Project {
    private String projectID;
    private String projectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Activity> activityList;
    private Employee projectLeader;

    public Project(String projectID, String projectName, LocalDate startDate, LocalDate endDate, List<Activity> activityList, Employee projectLeader) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activityList = activityList != null ? activityList : new ArrayList<>();
        this.projectLeader = projectLeader;
    }

    // le logic
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

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
    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setStartDate(LocalDate d) { this.startDate = d; }

    public void setEndDate(LocalDate d)   { this.endDate   = d; }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public void setProjectLeader(Employee projectLeader) {
        this.projectLeader = projectLeader;
    }
}
