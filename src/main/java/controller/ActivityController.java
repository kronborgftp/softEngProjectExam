package controller;

import model.*;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ActivityController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final ProjectView projectView;
    private final EmployeeView employeeView;
    private List<Activity> activityList = new ArrayList<>();

    public ActivityController(Scanner scanner, AppModel model, AppView appView,
                           ProjectView projectView, EmployeeView employeeView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.projectView = projectView;
        this.employeeView = employeeView;
    }

    public void addActivityToProject() {
        appView.prompt("Project ID");
        Project project = model.getProjectById(scanner.nextLine());
        if (project == null) {
            appView.printError("Project not found.");
            return;
        }

        appView.prompt("Activity ID");
        String id = scanner.nextLine();
        appView.prompt("Activity Name");
        String name = scanner.nextLine();
        appView.prompt("Budgeted Hours");
        int hours = Integer.parseInt(scanner.nextLine());
        appView.prompt("Start Week");
        int start = Integer.parseInt(scanner.nextLine());
        appView.prompt("End Week");
        int end = Integer.parseInt(scanner.nextLine());

        Activity activity = new Activity(id, name, hours, start, end);
        model.addActivityToProject(project, activity);
        projectView.printActivityAdded(activity.getActivityName());
    }

    public void assignEmployeeToActivity() {
        appView.prompt("Employee Initials");
        Employee employee = model.getEmployeeByInitials(scanner.nextLine());

        appView.prompt("Project ID");
        Project project = model.getProjectById(scanner.nextLine());

        if (employee == null || project == null) {
            appView.printError("Invalid employee or project.");
            return;
        }

        appView.prompt("Activity ID");
        Activity activity = model.getActivityInProject(project, scanner.nextLine());

        if (activity == null) {
            appView.printError("Activity not found.");
            return;
        }

        model.assignEmployeeToActivity(employee, activity);
        employeeView.printEmployeeAssignedToActivity(employee, activity);
    }
}

