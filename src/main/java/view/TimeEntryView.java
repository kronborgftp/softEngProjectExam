/**
 *
 *
 * @author Frederik and Lasse
 */
package view;

import model.Activity;
import model.Employee;
import model.Project;
import model.TimeEntry;

import java.util.List;

public class TimeEntryView {

    //Written by Frederik
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

    //Written by Frederik
    public void printTimeEntryUpdated() {
        System.out.println("Time entry updated successfully.");
    }

    //Written by Lasse
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

            String projectName = (p != null) ? p.getProjectName() : "---";
            String hourDisplay = (entry.getHours() == -1) ? "ABSENT" : String.format("%.1f", entry.getHours());

            System.out.printf("- %s (%s)\n", e.getName(), e.getInitials());
            System.out.printf("  • Project: %s\n", projectName);
            System.out.printf("    - Activity: %s (%s) | Hours: %s | Date: %s\n",
                    a.getActivityName(), a.getActivityId(), hourDisplay, entry.getDate());
        }
    }


    //Written by Frederik
    public void printTimeEntriesForEmployee(Employee employee, List<TimeEntry> entries) {
        System.out.println("\nTime entries for " + employee.getName() + ":");

        if (entries.isEmpty()) {
            System.out.println("No time entries found.");
            return;
        }

        int index = 1;
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-5s %-12s %-25s %-15s %-10s\n",
                "#", "Date", "Activity", "Project", "Hours/Status");
        System.out.println("---------------------------------------------------------------");

        for (TimeEntry entry : entries) {
            String date = entry.getDate();
            String activityName = entry.getActivity().getActivityName();
            Project parentProject = entry.getActivity().getParentProject();
            String projectName = (parentProject != null) ? parentProject.getProjectID() : "---";

            if (entry.getHours() == -1) {
                System.out.printf("%-5d %-12s %-25s %-15s %-10s\n",
                        index++, date, activityName, projectName, "ABSENT");
            } else {
                System.out.printf("%-5d %-12s %-25s %-15s %-10.1f\n",
                        index++, date, activityName, projectName, entry.getHours());
            }
        }

        System.out.println("---------------------------------------------------------------");
    }


    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }
}