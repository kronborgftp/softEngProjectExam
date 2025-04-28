package view;

import model.Activity;
import model.Employee;
import model.Project;
import model.TimeEntry;

import java.util.List;

public class TimeEntryView {

    public void printTimeLogged(TimeEntry entry) {
        System.out.println("Logged " + entry.getHours() + "h for " +
                entry.getEmployee().getInitials() + " on " +
                entry.getActivity().getActivityId() + " (" + entry.getDate() + ")");
    }


    public void printAllLoggedTime(List<TimeEntry> entries) {
        if (entries.isEmpty()) {
            System.out.println("No time entries recorded.");
            return;
        }

        System.out.println("\nAll Logged Hours:");

        for (TimeEntry entry : entries) {
            Employee e = entry.getEmployee();
            Activity a = entry.getActivity();
            Project p = a.getParentProject();

            System.out.printf("- %s (%s)\n", e.getName(), e.getInitials());
            System.out.printf("  • Project: %s\n", p != null ? p.getProjectName() : "Unknown");
            System.out.printf("    - Activity: %s (%s) | Hours: %.2f | Date: %s\n",
                    a.getActivityName(), a.getActivityId(), entry.getHours(), entry.getDate());
        }
    }

    public void printError(String message) {
        System.out.println(message);
    }

}
