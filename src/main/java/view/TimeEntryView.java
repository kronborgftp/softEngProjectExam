package view;

import model.Activity;
import model.Employee;
import model.Project;
import model.TimeEntry;

import java.util.List;

public class TimeEntryView {

    public void printTimeLogged(TimeEntry entry) {
        if (entry.getHours() == -1) {
            System.out.println("Marked " + entry.getEmployee().getInitials() +
                    " as absent (" + entry.getActivity().getActivityName() + ") on " +
                    entry.getDate());
        } else {
            System.out.println("Logged " + entry.getHours() + "h for " +
                    entry.getEmployee().getInitials() + " on " +
                    entry.getActivity().getActivityId() + " (" + entry.getDate() + ")");
        }
    }


    public void printTimeEntryUpdated() {
        System.out.println("Time entry updated successfully.");
    }

    public void printAllLoggedTime(List<TimeEntry> entries) {
        if (entries.isEmpty()) {
            System.out.println("No time entries recorded.");
            return;
        }

        System.out.println("\n===== All Logged Hours =====");

        for (TimeEntry entry : entries) {
            Employee e = entry.getEmployee();
            Activity a = entry.getActivity();
            Project p = a.getParentProject();

            System.out.printf("- %s (%s)\n", e.getName(), e.getInitials());
            System.out.printf("  • Project: %s\n", p != null ? p.getProjectName() : "Standard Activity");
            System.out.printf("    - Activity: %s (%s) | Hours: %.1f | Date: %s\n",
                    a.getActivityName(), a.getActivityId(), entry.getHours(), entry.getDate());
        }
    }

    public void printAssignedActivities(Employee employee, List<Activity> activities) {
        System.out.println("\nAssigned Activities for " + employee.getName() + ":");
        int index = 1;
        for (Activity activity : activities) {
            Project p = activity.getParentProject();
            System.out.printf("%d. [%s] %s - %s\n",
                    index++,
                    activity.getActivityId(),
                    activity.getActivityName(),
                    p != null ? p.getProjectName() : "Standard Activity");
        }
    }

    public void printActivitiesForProject(Project project) {
        System.out.println("\nActivities in project " + project.getProjectName() + ":");
        int index = 1;
        for (Activity activity : project.getActivityList()) {
            System.out.printf("%d. [%s] %s\n",
                    index++,
                    activity.getActivityId(),
                    activity.getActivityName());
        }
    }

    public void printMatchingActivities(List<Activity> activities) {
        System.out.println("\nMatching Activities:");
        int index = 1;
        for (Activity activity : activities) {
            Project p = activity.getParentProject();
            System.out.printf("%d. [%s] %s - %s\n",
                    index++,
                    activity.getActivityId(),
                    activity.getActivityName(),
                    p != null ? p.getProjectName() : "Standard Activity");
        }
    }

    public void printTimeEntriesForEmployee(Employee employee, List<TimeEntry> entries) {
        System.out.println("\nTime entries for " + employee.getName() + ":");

        if (entries.isEmpty()) {
            System.out.println("No time entries found.");
            return;
        }

        int index = 1;
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-5s %-12s %-25s %-15s %-10s\n",
                "#", "Date", "Activity", "Project", "Hours");
        System.out.println("---------------------------------------------------------------");

        for (TimeEntry entry : entries) {
            String date = entry.getDate();
            String activityName = entry.getActivity().getActivityName();
            String projectName = entry.getActivity().getParentProject() != null ?
                    entry.getActivity().getParentProject().getProjectID() : "Standard";
            double hours = entry.getHours();

            System.out.printf("%-5d %-12s %-25s %-15s %-10.1f\n",
                    index++, date, activityName, projectName, hours);
        }

        System.out.println("---------------------------------------------------------------");
    }

    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }
}