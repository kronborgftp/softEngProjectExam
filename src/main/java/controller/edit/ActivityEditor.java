package controller.edit;

import model.Activity;
import model.AppModel;
import view.AppView;
import view.ProjectView;
import controller.ActivityController;

import java.util.Scanner;



public class ActivityEditor {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final ProjectView projectView;
    private final ActivityController activityController;

    public ActivityEditor(Scanner scanner, AppModel model, AppView appView, ProjectView projectView, ActivityController activityController) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.projectView = projectView;
        this.activityController = activityController;
    }

    public void edit(Activity activity) {
        boolean editing = true;
        while (editing) {
            appView.printEditMenu("activity");
            switch (scanner.nextLine()) {
                case "1" -> changeName(activity);
                case "2" -> changeWeeks(activity);
                case "3" -> changeBudget(activity);
                case "0" -> editing = false;
                default -> appView.printError("Invalid input.");
            }
        }
    }

    private void changeName(Activity activity) {
        appView.prompt("New Activity Name");
        String name = scanner.nextLine();
        model.updateActivityName(activity, name);
        projectView.printInfo("Activity name updated.");
    }

    private void changeWeeks(Activity activity) {
        appView.prompt("New Start Week");
        int start = Integer.parseInt(scanner.nextLine());
        appView.prompt("New End Week");
        int end = Integer.parseInt(scanner.nextLine());
        model.updateActivityWeeks(activity, start, end);
        projectView.printInfo("Activity duration updated.");
    }

    private void changeBudget(Activity activity) {
        appView.prompt("New Budgeted Hours");
        int hours = Integer.parseInt(scanner.nextLine());
        model.updateBudgetedHours(activity, hours);
        projectView.printInfo("Budget updated.");
    }
}

