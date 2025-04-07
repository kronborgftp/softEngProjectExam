package model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    String projectID;
    String projectName;
    int startWeek;
    int endWeek;
    List<Activity> activityList = new ArrayList<>();
    Employee projectLeader;

    public Project(String projectID, String projectName, int startWeek, int endWeek, List<Activity> activityList,  Employee projectLeader) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.activityList = activityList;
        this.projectLeader = projectLeader;
    }

    public void addActivity(Activity activity) {

    }

    public void assignProjectLeader(Employee projectLeader) {

    }

    public Project getProject(String projectID) {
        return this;
    }
}
