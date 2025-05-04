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
        
        option("log in");
        option("Insert initials to log in");
        option("0. Exit");
        newLine();
        status();
        prompt("Input");
        
    }

    public void printMainMenu() {
        printMenu("time management system");
        option("1. Create Project");
        option("2. Add Activity to Project");
        option("3. Assign Employee to Activity");
        option("4. Log Time");
        option("5. View All Projects");
        option("6. View All Employees");
        option("7. View All logged time");
        option("8. Edit Project/Activity");
        option("0. Log Out");
        newLine();
        status();
        prompt("Choose option");
    }

    public void printEditSelectionMenu() {
        option("\n--- Edit Menu ---");
        option("1. Edit Project");
        option("2. Edit Activity");
        option("0. Exit");
        newLine();
        status();
        prompt("Choose option");
    }

    public void printEditMenu(String entity) {
        option("\n--- Edit " + capitalize(entity) + " ---");
        option("1. Change Name");
        option("2. Change Start/End Week");
        if (entity.equalsIgnoreCase("project")) {
            option("3. Change Leader");
        } else if (entity.equalsIgnoreCase("activity")) {
            option("3. Change Budgeted Hours");
        }
        option("0. Exit");
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
            option(statusMessage);
            statusMessage = null;
        }
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
        System.out.println("\n");
    }

    public void option(String option) {
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
        System.out.println(message);
    }

    public void printExit() {
        System.out.println("Exiting program...");
    }
}
