package view;

import model.Activity;

import java.util.List;

public class ActivityView {

    public void printAllActivities(List<Activity> activities) {
        System.out.println("\nAvailable Activities:");
        for (Activity a : activities) {
            String projectName = (a.getParentProject() != null)
                    ? a.getParentProject().getProjectName()
                    : "Standard Activity";
            System.out.printf("- [%s] %s (Project: %s)\n",
                    a.getActivityId(), a.getActivityName(), projectName);
        }
    }


}
