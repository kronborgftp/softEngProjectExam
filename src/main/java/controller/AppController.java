package controller;

import model.*;
import view.*;
import controller.StatusHolder;

import java.util.Scanner;

public class AppController {
    private final Scanner scanner = new Scanner(System.in);

    private final AppModel appModel = new AppModel();

    private final AppView appView = new AppView();
    private final EmployeeView employeeView = new EmployeeView();
    private final ProjectView projectView = new ProjectView();
    private final TimeEntryView timeEntryView = new TimeEntryView();
    private final ReportView reportView = new ReportView();
    private final ActivityView activityView = new ActivityView();
    private final FixedActivityView fixedActivityView = new FixedActivityView();


    private final EmployeeController employeeController = new EmployeeController(scanner, appModel, appView, employeeView);
    private final ProjectController projectController = new ProjectController(scanner, appModel, appView, projectView, employeeView);
    private final ActivityController activityController = new ActivityController(scanner, appModel, appView, projectView, employeeView);
    private final TimeEntryController timeEntryController = new TimeEntryController(scanner, appModel, appView, timeEntryView, activityView);
    private final ReportController reportController = new ReportController(scanner, appModel, appView, reportView);
    private final FixedActivityController fixedActivityController = new FixedActivityController(
            scanner, appModel, appView, fixedActivityView
    );

    private String statusMessage;

    public void run() {
        boolean running = true;

        while (running) {
            appView.printStartMenu();
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    employeeController.promptLogIn();
                    if (AppModel.getLoggedIn() != null) {
                        mainMenu(AppModel.getLoggedIn());
                    }
                }
                case "2" -> employeeController.promptRegisterEmployee(); 
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
        AppModel.setLoggedIn(e);

        // boolean loggedIn = true; // loggedIn er også en variabel i employee controller... skal tjekkes
        while (AppModel.getLoggedIn() == e) {
            appView.printMainMenu();

            String input = scanner.nextLine();

            switch (input) { // edit projects, log absence, 
                case "1" -> timeEntryController.logTime();  // log time
                case "2" -> manageProjects();  // manage projects // AKA edit menu
                case "3" -> fixedActivityController.logAbsence();  // log absence
                case "4" -> employeeController.showAllEmployees();  // show all employees
                // case "1" -> timeEntryController.logTime();                       //1 DONE
                // case "2" -> fixedActivityController.logAbsence();                //3 DONE 
                // case "3" -> timeEntryController.showEmployeeLoggedHours();       
                // case "4" -> editMenu();                                          //2 ???
                // case "5" -> projectController.createProject();                   //2 DONE
                // case "6" -> activityController.addActivityToProject();           //2 per project DONE
                // case "7" -> activityController.assignEmployeeToActivity();       //2 per activity per project 
                // case "8" -> projectController.showAllProjects();                 //2 manage projects
                // case "9" -> employeeController.showAllEmployees();               //4 DONE
                // case "10" -> timeEntryController.showAllLoggedHours();           //2 
                // case "11" -> reportController.projectTimeReport();               //2 per project
                // case "12" -> reportController.employeeTimeReport();              //2 per project


                case "0" -> AppModel.setLoggedIn(null);
                default -> appView.printError("Invalid input.");
            }
        }
    }

    private void manageProjects() {
        boolean managingProjects = true;

        while (managingProjects) {
            appView.printProjectManager();

            switch (scanner.nextLine()) {
                case "1" -> {               // skal display projekter så man kan vælge mellem dem
                    appView.prompt("Project ID");
                    Project p = appModel.getProjectById(scanner.nextLine());
                    if (p == null) appView.printError("Project not found.");
                    else editProject(p);

                }
                case "2" -> projectController.createProject();
                case "3" -> timeEntryController.showAllLoggedHours(); 
                case "4" -> projectController.showAllProjects();
                case "0" -> managingProjects = false;
                default -> appView.printError("Invalid input.");
            }

        }
    }

    private void editProject(Project p) {
        Project editingProject = p;
        AppModel.setCurrentProject(p);

        while (editingProject != null) {
            appView.printEditProject();

            switch(scanner.nextLine()) {
                case "1" -> manageActivities(p);
                case "2" -> activityController.addActivityToProject();  
                case "3" -> projectController.changeName(p);
                case "4" -> projectController.changeWeeks(p);
                case "5" -> projectController.changeLeader(p);
                case "6" -> reportController.projectTimeReport();
                case "0" -> {
                    editingProject = null;
                    AppModel.setCurrentProject(null);
                }
                default -> appView.printError("Invalid input.");
            }
        }
    }

    private void manageActivities(Project p) {
        Project managingActivities = p;

        while (managingActivities != null) {
            appView.printActivityManager();

            switch(scanner.nextLine()) {
                case "1" -> {                     // skal display aktiviteter i givne projekt så man kan vælge dem
                    appView.prompt("Activity ID");
                    Activity a = appModel.getActivityInProject(p, scanner.nextLine());
                    if (a == null) appView.printError("Activity not found.");
                    else editActivity(a);
                }   // edit activity
                case "2" -> timeEntryController.logTime(); // log time // skal tilføje time entries her
                case "3" -> timeEntryController.editTimeEntry(AppModel.getLoggedIn()); // edit logtime // skal tilføje ændring af time entries her
                
                case "0" -> managingActivities = null;
                default -> appView.printError("Invalid input.");
            }
        }
    }

    private void editActivity(Activity a) {
        Activity editingActivity = a;
        AppModel.setCurrentActivity(a);

        while (editingActivity != null) {
            appView.printEditActivity();

            switch(scanner.nextLine()) {
                case "1" -> activityController.changeName(a);
                case "2" -> activityController.changeWeeks(a);
                case "3" -> activityController.changeBudget(a);

                case "0" -> {
                    editingActivity = null;
                    AppModel.setCurrentActivity(null);
                }
                default -> appView.printError("Invalid input.");
            }
        }
    }

    public AppModel getModel() {
        return appModel;
    }

}
