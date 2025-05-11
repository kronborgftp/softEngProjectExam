/**
 *
 *
 * @author Frederik (and Kim)
 */
package controller;

import model.Activity;
import model.AppModel;
import model.Project;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ActivityController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final ProjectView projectView;
    private final EmployeeView employeeView;


    public ActivityController(Scanner scanner, AppModel model, AppView appView,
                           ProjectView projectView, EmployeeView employeeView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.projectView = projectView;
        this.employeeView = employeeView;
    }

    //written by Frederik
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

    //written by Frederik and Lasse refactored by Kim (Moved from other class)
    public void changeName(Activity activity) {
        appView.prompt("New name");
        String newName = scanner.nextLine();

        if (newName == null || newName.trim().isEmpty()) {
            projectView.printError("Invalid name input.");
            return;
        }

        if (newName.equals(activity.getActivityName())) {
            projectView.printError("Name must be different.");
            return;
        }

        activity.setActivityName(newName);
        projectView.printInfo("Activity name updated.");
    }

    //written by Frederik and Lasse refactored by Kim (Moved from other class)
    public void changeWeeks(Activity activity) {
        appView.prompt("New Start Week");
        String s1 = scanner.nextLine();
        int startWeek;
        try {
            startWeek = Integer.parseInt(s1);
        } catch (NumberFormatException e) {
            projectView.printError("Invalid week input.");
            return;
        }

        appView.prompt("New End Week");
        String s2 = scanner.nextLine();
        int endWeek;
        try {
            endWeek = Integer.parseInt(s2);
        } catch (NumberFormatException e) {
            projectView.printError("Invalid week input.");
            return;
        }

        if (endWeek < startWeek) {
            projectView.printError("End week must be ≥ start week.");
            return;
        }

        model.updateActivityWeeks(activity, startWeek, endWeek);
        projectView.printInfo("Activity duration updated.");
    }

    //written by Frederik and Lasse refactored by Kim (Moved from other class)
    public void changeBudget(Activity activity) {
        // prompt the user
        appView.prompt("New budget (hours)");

        String line = scanner.nextLine();
        int newBudget;
        try {
            newBudget = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            // non‐numeric input
            projectView.printError("Invalid budget input.");
            return;
        }

        if (newBudget < 0) {
            // negative numbers not allowed
            projectView.printError("Budget must be non‐negative.");
            return;
        }

        // everything’s OK then perform the change
        activity.setBudgetedHours(newBudget);
        projectView.printInfo("Activity budget updated.");
    }
}

