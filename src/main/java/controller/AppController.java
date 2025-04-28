package controller;

import controller.*;
import controller.edit.*;
import model.*;
import view.*;

import java.util.Scanner;

public class AppController {
    private final Scanner scanner = new Scanner(System.in);

    private final AppModel appModel = new AppModel();

    private final AppView appView = new AppView();
    private final EmployeeView employeeView = new EmployeeView();
    private final ProjectView projectView = new ProjectView();
    private final TimeEntryView timeEntryView = new TimeEntryView();

    private final ProjectEditor projectEditor = new ProjectEditor(scanner, appModel, appView, projectView, employeeView);
    private final ActivityEditor activityEditor = new ActivityEditor(scanner, appModel, appView, projectView);

    private final EmployeeController employeeController = new EmployeeController(scanner, appModel, appView, employeeView);
    private final ProjectController projectController = new ProjectController(scanner, appModel, appView, projectView, employeeView);
    private final ActivityController activityController = new ActivityController(scanner, appModel, appView, projectView, employeeView);
    private final TimeEntryController timeEntryController = new TimeEntryController(scanner, appModel, appView, timeEntryView);

    public void run() {
        boolean running = true;

        while (running) {
            appView.printMainMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> employeeController.registerEmployee();
                case "2" -> projectController.createProject();
                case "3" -> activityController.addActivityToProject();
                case "4" -> activityController.assignEmployeeToActivity();
                case "5" -> timeEntryController.logTime();
                case "6" -> projectController.showAllProjects();
                case "7" -> employeeController.showAllEmployees();
                case "8" -> timeEntryController.showAllLoggedHours();
                case "9" -> editMenu();
                case "0" -> running = false;
                default -> appView.printError("Invalid input.");
            }
        }

        appView.printExit();
    }



    private void editMenu() {
        boolean editing = true;
        while (editing) {
            appView.printEditSelectionMenu();
            switch (scanner.nextLine()) {
                case "1" -> {
                    appView.prompt("Project ID");
                    Project p = appModel.getProjectById(scanner.nextLine());
                    if (p == null) appView.printError("Project not found.");
                    else projectEditor.edit(p);
                }
                case "2" -> {
                    appView.prompt("Project ID");
                    Project project = appModel.getProjectById(scanner.nextLine());
                    if (project == null) {
                        appView.printError("Project not found.");
                        break;
                    }
                    appView.prompt("Activity ID");
                    Activity a = appModel.getActivityInProject(project, scanner.nextLine());
                    if (a == null) appView.printError("Activity not found.");
                    else activityEditor.edit(a);
                }
                case "0" -> editing = false;
                default -> appView.printError("Invalid input.");
            }
        }
    }
}
