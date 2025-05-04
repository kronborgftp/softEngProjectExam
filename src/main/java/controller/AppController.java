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
            appView.printStartMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    employeeController.logIn();
                    if (employeeController.getLoggedIn() != null) {
                        mainMenu(employeeController.getLoggedIn());
                    }
                }
                case "2" -> employeeController.registerEmployee(); 
                case "3" -> employeeController.showAllEmployees(); // skal ændres til horisontal liste
                // case "9" -> testMenu();
                case "0" -> running = false;
            }


        }

        appView.printExit();
    }

    // private void logInMenu() {
    //     Boolean logInAttempt = true;

    //     while (logInAttempt) {
    //         appView.printLogInMenu();
    //         String input = scanner.nextLine();


    //     } 
    // }

    private void mainMenu(Employee e) {
        employeeController.setLoggedIn(e);

        // boolean loggedIn = true; // loggedIn er også en variabel i employee controller... skal tjekkes
        while (employeeController.getLoggedIn() == e) {
            appView.printMainMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> projectController.createProject(); // employeeController.registerEmployee();
                case "2" -> activityController.addActivityToProject();
                case "3" -> activityController.assignEmployeeToActivity();
                case "4" -> timeEntryController.logTime();
                case "5" -> projectController.showAllProjects();
                case "6" -> employeeController.showAllEmployees();
                case "7" -> timeEntryController.showAllLoggedHours();
                case "8" -> editMenu();
                case "0" -> employeeController.setLoggedIn(null);// running baseret på boolean i starten af mainMenu(), hvordan passer den boolean ind?
                default -> appView.printError("Invalid input.");
            }
        }
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
