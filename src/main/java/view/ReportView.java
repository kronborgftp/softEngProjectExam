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

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportView {

    private static final DateTimeFormatter DANISH = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(new Locale("da", "DK"));

    //Written by Frederik and Lasse
    public void printProjectTimeReport(Project project, Map<String, Double> hoursPerActivity,
                                       Map<String, Integer> budgetPerActivity) {
        System.out.println("\n========== PROJECT TIME REPORT ==========");
        System.out.println("Project: " + project.getProjectName() + " (" + project.getProjectID() + ")");
        System.out.println("Duration: Week " + project.getStartDate().format(DANISH) + " - Week " + project.getEndDate().format(DANISH));
        System.out.println("Project Leader: " + project.getProjectLeader().getName() +
                " (" + project.getProjectLeader().getInitials() + ")");

        System.out.println("\nActivity Time Summary:");
        System.out.println("-----------------------------------------------------------------");
        System.out.printf("%-10s %-25s %-10s %-10s %-10s\n",
                "ID", "Name", "Budget", "Actual", "Remaining");
        System.out.println("-----------------------------------------------------------------");

        double totalBudget = 0;
        double totalActual = 0;

        for (Activity activity : project.getActivityList()) {
            String activityId = activity.getActivityId();
            String activityName = activity.getActivityName();
            int budget = budgetPerActivity.getOrDefault(activityId, 0);
            double actual = hoursPerActivity.getOrDefault(activityId, 0.0);
            double remaining = budget - actual;

            System.out.printf("%-10s %-25s %-10d %-10.1f %-10.1f\n",
                    activityId, activityName, budget, actual, remaining);

            totalBudget += budget;
            totalActual += actual;
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("%-10s %-25s %-10.1f %-10.1f %-10.1f\n",
                "", "TOTAL", totalBudget, totalActual, totalBudget - totalActual);
        System.out.println("-----------------------------------------------------------------");

        if (totalBudget > 0) {
            double percentComplete = (totalActual / totalBudget) * 100;
            System.out.printf("Project Completion: %.1f%%\n", percentComplete);
        }
    }

    //Written by Frederik and Lasse
    public void printEmployeeTimeReport(Employee employee, List<TimeEntry> entries, Map<String, Double> hoursPerDay) {
        System.out.println("\n========== EMPLOYEE TIME REPORT ==========");
        System.out.println("Employee: " + employee.getName() + " (" + employee.getInitials() + ")");
        System.out.println("\nRegistered Time Entries:");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-12s %-25s %-15s %-10s\n",
                "Date", "Activity", "Project", "Hours/Status");
        System.out.println("---------------------------------------------------------------");

        for (TimeEntry entry : entries) {
            String date = entry.getDate();
            String activityName = entry.getActivity().getActivityName();
            Project project = entry.getActivity().getParentProject();
            String projectName = (project != null) ? project.getProjectID() : "-";

            if (entry.getHours() == -1) {
                System.out.printf("%-12s %-25s %-15s %-10s\n",
                        date, activityName, "-", "ABSENT");
            } else {
                System.out.printf("%-12s %-25s %-15s %-10.1f\n",
                        date, activityName, projectName, entry.getHours());
            }
        }

        System.out.println("---------------------------------------------------------------");

        System.out.println("\nDaily Summary:");
        System.out.println("----------------");
        double totalHours = 0;
        for (Map.Entry<String, Double> dayEntry : hoursPerDay.entrySet()) {
            System.out.printf("%-12s: %.1f hours\n", dayEntry.getKey(), dayEntry.getValue());
            totalHours += dayEntry.getValue();
        }

        System.out.println("----------------");
        System.out.printf("Total Work Hours: %.1f\n", totalHours);
    }



    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }
}
