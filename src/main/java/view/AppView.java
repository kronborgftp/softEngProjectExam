package view;

import java.util.List;

import model.Employee;
import model.AppModel;
import controller.EmployeeController;
import controller.StatusHolder;

import java.util.ArrayList;

public class AppView {
    // private String statusMessage;
    // private Employee loggedIn;

    public void printStartMenu() {
        menuTitle("start menu");
        option(1,"Log In");
        option(2,"Register");
        option(3,"View all employees"); // WIP, horisontal menu
        option(0,"Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    public void printLogInMenu() {
        print("log in");
        print("Insert initials to log in");
        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Input");
        
    }

    public void printMainMenu() {
        menuTitle("time management system");
        loggedIn();
        newLine();
        print("1. Log time for today");
        print("2. Manage projects");
        print("3. Log absence");
        print("4. Show all employees");
        // print("1. Log time for today");
        // print("2. Log Vacation/Sick/Course");
        // print("3. Show Employee's Logged Hours");
        // print("4. Edit Project/Activity/Time Entry");
        // print("5. Create Project");
        // print("6. Add Activity to Project");
        // print("7. Assign Employee to Activity");
        // print("8. View All Projects");
        // print("9. View All Employees");
        // print("10. View All Logged Time");
        // print("11. Project Time Report");
        // print("12. Employee Time Report");

        print("0. Log Out");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    public void printProjectManager() {
        menuTitle("project manager");
        loggedIn();
        newLine();
        print("1. Edit Project");
        print("2. Create Project");
        print("3. View All Logged Time");
        
        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    public void printEditProject() {
        menuTitle("edit project");
        currentProject();
        newLine();
        print("1. Manage activities");
        print("2. Assign activity to project");
        print("3. Change name");
        print("4. Change period");
        print("5. Change leader");
        print("6. Project time report");

        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    public void printActivityManager() {
        menuTitle("activity manager");
        currentProject();
        newLine();
        print("1. Edit activities");
        print("2. Log time entry - WIP");
        print("3. Edit logged time entry - WIP");

        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    public void printEditActivity() {        
        menuTitle("edit activity");
        currentProject();
        currentActivity();
        newLine();
        print("1. Change name");
        print("2. Change period");
        print("3. Change timebudget");

        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    public void printEditSelectionMenu() {
        menuTitle("edit menu");
        // print("\n--- Edit Menu ---");
        print("1. Edit Project");
        print("2. Edit Activity");
        print("3. Edit Time Entry");
        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    public void printEditMenu(String entity) {
        print("\n--- Edit " + capitalize(entity) + " ---");
        loggedIn();
        print("1. Change Name");
        print("2. Change Start/End Week");
        if (entity.equalsIgnoreCase("project")) {
            print("3. Change Leader");
        } else if (entity.equalsIgnoreCase("activity")) {
            print("3. Change Budgeted Hours");
        }
        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public void doubleDisplay(List<List<String>> leftDisplay, List<List<String>> rightDisplay) { // left og right menu variabler til kombinationer
        // printer række-vis. så først øverste, så næste række, osv
        // første for loop kører gennem rækker, anden går kolonne-vis, så fra venstre til højre

        // for (int i = 0; i < leftDisplay.size(); i++) {
        //     for (int j = 0; i < leftDisplay.size() + rightDisplay.size())
        // }



    }

    // public List<List<String>> leftExample() {
    //     List<List<String>> leftMenu = new ArrayList<List<String>>();
    //     for (int i = 0; i < 3; i++ ) {

    //     }
    // }

    public void loggedIn() {
        if (AppModel.getLoggedIn() != null) {
            print("    Currently logged in: " + AppModel.getLoggedIn().getName() + " (" + AppModel.getLoggedIn().getInitials() + ")");
        }
    }

    public void currentProject() {
        print("    Current project: " + 
        AppModel.getCurrentProject().getProjectID() + 
        " (" + AppModel.getCurrentProject().getProjectName() + ")");
    }

    public void currentActivity() {
        print("    Current activity: " + 
        AppModel.getCurrentActivity().getActivityId() + 
        " (" + AppModel.getCurrentActivity().getActivityName() + ")");
    }

    public void menuTitle(String menu) {
        System.out.print("\n-==%%%  ");
        for (int i = 0; i < menu.length(); i++) {
            char letter = menu.charAt(i);
            System.out.print(" " + String.valueOf(letter).toUpperCase());
        }
        System.out.println("   %%%==-");
    }

    public void newLine() {
        System.out.print("\n");
    }

    public void print(String option) {
        System.out.println(option);
    }

    public void option(int nr, String option) {
        System.out.println(String.valueOf(nr) + ". " + option);
    }

    public void prompt(String message) {
        System.out.print(message + ": ");
    }

    public void printInfo(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }

    public void printExit() {
        System.out.println("Exiting program...");
    }
}