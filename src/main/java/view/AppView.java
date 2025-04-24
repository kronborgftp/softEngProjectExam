package view;


public class AppView {

    public void printMainMenu() {
        System.out.println("\n=== TIME MANAGEMENT SYSTEM ===");
        System.out.println("1. Register Employee");
        System.out.println("2. Create Project");
        System.out.println("3. Add Activity to Project");
        System.out.println("4. Assign Employee to Activity");
        System.out.println("5. Log Time");
        System.out.println("6. View All Projects");
        System.out.println("7. View All Employees");
        System.out.println("8. View All logged time");
        System.out.println("9. Edit Project/Activity");
        System.out.println("0. Exit");
        prompt("Choose option");
    }

    public void printEditSelectionMenu() {
        System.out.println("\n--- Edit Menu ---");
        System.out.println("1. Edit Project");
        System.out.println("2. Edit Activity");
        System.out.println("0. Back");
        prompt("Choose option");
    }

    public void printEditMenu(String entity) {
        System.out.println("\n--- Edit " + capitalize(entity) + " ---");
        System.out.println("1. Change Name");
        System.out.println("2. Change Start/End Week");
        if (entity.equalsIgnoreCase("project")) {
            System.out.println("3. Change Leader");
        } else if (entity.equalsIgnoreCase("activity")) {
            System.out.println("3. Change Budgeted Hours");
        }
        System.out.println("0. Back");
        prompt("Choose option");
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
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
