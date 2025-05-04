package controller;

import model.*;
import view.AppView;
import view.ReportView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReportController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final ReportView reportView;

    public ReportController(Scanner scanner, AppModel model, AppView appView, ReportView reportView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.reportView = reportView;
    }

    public void projectTimeReport() {
        appView.prompt("Project ID");
        String projectId = scanner.nextLine();

        Project project = model.getProjectById(projectId);
        if (project == null) {
            reportView.printError("Project not found.");
            return;
        }

        // Get all time entries for this project
        List<TimeEntry> projectEntries = model.getAllTimeEntries().stream()
                .filter(entry -> {
                    Activity activity = entry.getActivity();
                    return activity != null &&
                            activity.getParentProject() != null &&
                            activity.getParentProject().getProjectID().equals(projectId);
                })
                .collect(Collectors.toList());

        // Calculate hours per activity
        Map<String, Double> hoursPerActivity = new HashMap<>();
        Map<String, Integer> budgetPerActivity = new HashMap<>();

        for (Activity activity : project.getActivityList()) {
            hoursPerActivity.put(activity.getActivityId(), 0.0);
            budgetPerActivity.put(activity.getActivityId(), activity.getBudgetedHours());
        }

        for (TimeEntry entry : projectEntries) {
            String activityId = entry.getActivity().getActivityId();
            hoursPerActivity.put(activityId,
                    hoursPerActivity.getOrDefault(activityId, 0.0) + entry.getHours());
        }

        // Print report
        reportView.printProjectTimeReport(project, hoursPerActivity, budgetPerActivity);
    }

    public void employeeTimeReport() {
        appView.prompt("Employee Initials");
        String initials = scanner.nextLine();

        Employee employee = model.getEmployeeByInitials(initials);
        if (employee == null) {
            reportView.printError("Employee not found.");
            return;
        }

        // Get all time entries for this employee
        List<TimeEntry> employeeEntries = model.getAllTimeEntries().stream()
                .filter(entry -> entry.getEmployee().getInitials().equalsIgnoreCase(initials))
                .toList();

        // Calculate daily total hours (ignore absences)
        Map<String, Double> hoursPerDay = new HashMap<>();
        for (TimeEntry entry : employeeEntries) {
            if (entry.getHours() != -1) {
                String date = entry.getDate();
                hoursPerDay.put(date,
                        hoursPerDay.getOrDefault(date, 0.0) + entry.getHours());
            }
        }

        // Show the full report (absences included in entry list, excluded from total)
        reportView.printEmployeeTimeReport(employee, employeeEntries, hoursPerDay);
    }


}
