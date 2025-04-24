package view;

import model.Project;
import model.Activity;

import java.util.List;

public class ProjectView {

    public void printProjectCreated(Project p) {
        System.out.println("Created project: " + p.getProjectName() + " (" + p.getProjectID() + ")");
    }

    public void printActivityAdded(String activityName) {
        System.out.println("Added activity: " + activityName);
    }

    public void printInfo(String message) {
        System.out.println(message);
    }


    public void printProject(Project p) {
        System.out.println("\n--- Project: " + p.getProjectName() + " (" + p.getProjectID() + ") ---");
        System.out.println("Duration: Week " + p.getStartWeek() + " to " + p.getEndWeek());
        if (p.getProjectLeader() != null) {
            System.out.println("Leader: " + p.getProjectLeader().getName());
        } else {
            System.out.println("Leader: Not assigned");
        }
        System.out.println("Activities:");
        for (Activity a : p.getActivityList()) {
            System.out.println("  • " + a.getActivityName() + " [" + a.getActivityId() + "]");
        }
    }

    public void printProjectList(List<Project> projects) {
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        for (Project p : projects) {
            printProject(p);
        }
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
