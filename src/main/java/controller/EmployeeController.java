package controller;

import model.AppModel;
import model.Employee;
import view.AppView;
import view.EmployeeView;

import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.List;
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

    public void registerEmployee() {
        appView.prompt("Initials");
        String initials = scanner.nextLine();

        if (initials.equals("0")) {
            String errorMSG = "Register cancelled - Returning to start menu";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());

        } else if (model.hasEmployee(initials)) {
            String errorMSG = "Initials already registered";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());
            
        } else if (initials.equals("")) {
            String errorMSG = "Cannot have empty initials";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());

        } else if (!initials.matches("[a-zA-Z]+")) {
            String errorMSG = "Cannot use non-letters in initials";
            StatusHolder.setStatus(errorMSG);
            System.out.println(StatusHolder.getStatus());
            
        } else if (initials != null) {
            initials = (initials != null && initials.length() > 4) ? initials.substring(0, 4) : initials;
            appView.prompt("Full name");
            String name = scanner.nextLine();
    
            Employee e = new Employee(initials, name, new ArrayList<>());
            model.addEmployee(e);
            employeeView.printEmployeeRegistered(e);

        } //else {
        //     appView.setStatus("ERROR: Register failed - Invalid initials");;
        // }

    }
    
    public void logIn() { 
        System.out.println("\n--- Log In");
        System.out.println("Insert initials to log in");
        System.out.println("0. Exit");
        appView.prompt("Input");
        String initials = scanner.nextLine();

        Employee e = model.getEmployeeByInitials(initials);

        if (model.hasEmployee(initials)) {
            AppModel.setLoggedIn(e);
            employeeView.printEmployeeLoggedIn(e);
            
        } else {
            employeeView.printError("ERROR: input not recognized. Input correct initials or register new employee");
        }

    }

    // public Employee getLoggedIn() {
    //     return loggedIn;
    // }

    // public void setLoggedIn(Employee e) {
    //     loggedIn = e;
    // }

    public void showAllEmployees() {
        employeeView.printEmployeeList(model.getAllEmployees());
    }    
}
