/**
 *
 *
 * @author Kim (and Frederik)
 *
 * Frederik created the class, and made the first version. Kim wrote the version that you see today.
 */
package controller;

import model.AppModel;
import model.Employee;
import view.AppView;
import view.EmployeeView;

import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final EmployeeView employeeView;
    // private Employee loggedIn = null;

        // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public EmployeeController(Scanner scanner, AppModel model, AppView appView, EmployeeView employeeView) { 
        this.scanner = scanner; 
        this.model = model;
        this.appView = appView;
        this.employeeView = employeeView;
    }

    public void promptRegisterEmployee() {
        appView.prompt("Initials");
        String initials = scanner.nextLine();

        appView.prompt("Full name");
        String name = scanner.nextLine();

        registerEmployee(initials,name);
    }    

    public void registerEmployee(String initials, String name) {
        
        if (initials.equals("0")) {
            String errorMSG = "Register cancelled - Returning to start menu";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());

        } else if (model.hasEmployee(initials)) {
            String errorMSG = "Initials already registered";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());
            
        } else if (initials.equals("") || (initials.matches(" ") && !initials.matches("[a-zA-Z]+"))) {
            String errorMSG = "Cannot have empty initials";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());

        } else if (!initials.matches("[a-zA-Z]+")) {
            String errorMSG = "Cannot use non-letters in initials";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());
            
        } else if (initials != null) {
            initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
    
            Employee e = new Employee(initials, name, new ArrayList<>());
            model.addEmployee(e);
            employeeView.printEmployeeRegistered(e);

        } 
    }
    
    public void promptLogIn() {
        System.out.println("\n--- Log In");
        System.out.println("Insert initials to log in");
        System.out.println("0. Exit");
        appView.prompt("Initials");
        String initials = scanner.nextLine();

        logIn(initials);
    }

    public void logIn(String initials) { 
        Employee e = model.getEmployeeByInitials(initials);

        if (model.hasEmployee(initials)) {
            AppModel.setLoggedIn(e);
            employeeView.printEmployeeLoggedIn(e);
            
        } else {
            employeeView.printError("Input not recognized");
        }

    }


    public void showAllEmployees() {
        employeeView.printEmployeeList(model.getAllEmployees());
    }    
}
