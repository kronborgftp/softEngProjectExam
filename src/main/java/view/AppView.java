/**
 *
 *
 * @author Frederik, Lasse and Kim
 * Most methods are written by Kim with input from both Lasse and Frederik
 */
package view;


import controller.StatusHolder;
import model.AppModel;

public class AppView {

    //Written by Kim
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


    //Written by Kim
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

    //Written by Kim
    public void printProjectManager() {
        menuTitle("project manager");
        loggedIn();
        newLine();
        print("1. Edit Project");
        print("2. Create Project");
        print("3. View All Logged Time");
        print("4. Show all projects");
        
        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    //Written by Kim
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

    //Written by Kim
    public void printActivityManager() {
        menuTitle("activity manager");
        currentProject();
        newLine();
        print("1. Edit activities");
        print("2. Log time entry");
        print("3. Edit logged time entry");

        print("0. Exit");
        newLine();
        StatusHolder.getStatus();
        prompt("Choose option");
    }

    //Written by Kim
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

    //Written by Kim
    public void loggedIn() {
        if (AppModel.getLoggedIn() != null) {
            print("    Currently logged in: " + AppModel.getLoggedIn().getName() + " (" + AppModel.getLoggedIn().getInitials() + ")");
        }
    }

    //Written by Kim
    public void currentProject() {
        print("    Current project: " + 
        AppModel.getCurrentProject().getProjectID() + 
        " (" + AppModel.getCurrentProject().getProjectName() + ")");
    }

    //Written by Kim
    public void currentActivity() {
        print("    Current activity: " + 
        AppModel.getCurrentActivity().getActivityId() + 
        " (" + AppModel.getCurrentActivity().getActivityName() + ")");
    }

    //Written by Kim
    public void menuTitle(String menu) {
        System.out.print("\n-==%%%  ");
        for (int i = 0; i < menu.length(); i++) {
            char letter = menu.charAt(i);
            System.out.print(" " + String.valueOf(letter).toUpperCase());
        }
        System.out.println("   %%%==-");
    }

    //Written by Kim
    public void newLine() {
        System.out.print("\n");
    }

    //Written by Kim
    public void print(String option) {
        System.out.println(option);
    }

    //Written by Kim
    public void option(int nr, String option) {
        System.out.println(String.valueOf(nr) + ". " + option);
    }

    //Written by Frederik
    public void prompt(String message) {
        System.out.print(message + ": ");
    }

    //Written by Frederik
    public void printInfo(String message) {
        System.out.println(message);
    }

    //Written by Frederik
    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }

    //Written by Frederik
    public void printExit() {
        System.out.println("Exiting program...");
    }
}