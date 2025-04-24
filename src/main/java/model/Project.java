package model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String projectID;
    private String projectName;
    private int startWeek;
    private int endWeek;
    private List<Activity> activityList;
    private Employee projectLeader;

    public Project(String projectID, String projectName, int startWeek, int endWeek, List<Activity> activityList, Employee projectLeader) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
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

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

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

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public void setProjectLeader(Employee projectLeader) {
        this.projectLeader = projectLeader;
    }
}
