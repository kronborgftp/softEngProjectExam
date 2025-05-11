/**
 *
 *
 * @author Frederik and Lasse
 */
package view;

import model.Activity;
import model.Project;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ProjectView {
    private static final DateTimeFormatter DANISH = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(new Locale("da", "DK"));

    //Written by Lasse
    public void printProjectCreated(Project p) {
        System.out.println("Created project: " + p.getProjectName() + " (" + p.getProjectID() + ")");
        System.out.println("  Duration: " + p.getStartDate().format(DANISH) + " til " + p.getEndDate().format(DANISH));
    }

    //Written by Lasse
    public void printActivityAdded(String activityName) {
        System.out.println("Added activity: " + activityName);
    }

    //Written by Lasse
    public void printInfo(String message) {
        System.out.println(message);
    }


    //Written by Lasse
    public void printProject(Project p) {
        System.out.println("\n--- Project: " + p.getProjectName() + " (" + p.getProjectID() + ") ---");
        System.out.println("Duration: Week " + p.getStartDate() + " to " + p.getEndDate());
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

    //Written by Frederik
    public void printProjectList(List<Project> projects) {
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        for (Project p : projects) {
            printProject(p);
        }
    }

    //Written by Lasse
    public void printError(String message) {
        System.out.println(message);
    }
}
