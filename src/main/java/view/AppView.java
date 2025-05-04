package view;

public class AppView {
    private String statusMessage;

    public void printStartMenu() {
        printMenu("start menu");
        option(1,"Log In");
        option(2,"Register");
        option(3,"View all employees"); // WIP, horisontal menu
        option(0,"Exit");
        newLine();
        status();
        prompt("Choose option");
    }

    public void printLogInMenu() {
        
        print("log in");
        print("Insert initials to log in");
        print("0. Exit");
        newLine();
        status();
        prompt("Input");
        
    }

    public void printMainMenu() {
        printMenu("time management system");
        print("1. Create Project");
        print("2. Add Activity to Project");
        print("3. Assign Employee to Activity");
        print("4. Log Time");
        print("5. View All Projects");
        print("6. View All Employees");
        print("7. View All Logged Time");
        print("8. Edit Project/Activity/Time Entry");
        System.out.println("10. Project Time Report");
        System.out.println("11. Employee Time Report");
        System.out.println("12. Log Vacation/Sick/Course");
        System.out.println("13. Show Employee's Logged Hours");
        print("0. Log Out");
        newLine();
        status();
        prompt("Choose option");
    }

    public void printEditSelectionMenu() {
        print("\n--- Edit Menu ---");
        print("1. Edit Project");
        print("2. Edit Activity");
        print("3. Edit Time Entry");
        print("0. Exit");
        newLine();
        status();
        prompt("Choose option");
    }


    public void printEditMenu(String entity) {
        print("\n--- Edit " + capitalize(entity) + " ---");
        print("1. Change Name");
        print("2. Change Start/End Week");
        if (entity.equalsIgnoreCase("project")) {
            print("3. Change Leader");
        } else if (entity.equalsIgnoreCase("activity")) {
            print("3. Change Budgeted Hours");
        }
        print("0. Exit");
        newLine();
        status();
        prompt("Choose option");
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public void setStatus(String msg) {
        statusMessage = msg;
    }

    public void status() {
        if (statusMessage != null) {
            print("--==%%   S T A T U S   -   " + statusMessage);
            statusMessage = null;
        }
        newLine();
    }

    public void printMenu(String menu) {
        System.out.print("\n---===%%%  ");
        for (int i = 0; i < menu.length(); i++) {
            char letter = menu.charAt(i);
            System.out.print(" " + String.valueOf(letter).toUpperCase());
        }
        System.out.println("   %%%===---");
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