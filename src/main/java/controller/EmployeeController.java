package controller;

import model.AppModel;
import model.Employee;
import view.AppView;
import view.EmployeeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final EmployeeView employeeView;
    private List<Employee> employeeList = new ArrayList<>();
    private String loggedIn = null;

    public EmployeeController(Scanner scanner, AppModel model, AppView appView, EmployeeView employeeView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.employeeView = employeeView;
    }

    public void registerEmployee() {
        appView.prompt("Initials");
        String initials = scanner.nextLine();
        appView.prompt("Full name");
        String name = scanner.nextLine();

        Employee e = new Employee(initials, name, new ArrayList<>());
        model.addEmployee(e);
        employeeView.printEmployeeRegistered(e);
    }
    
    public boolean hasEmployee(String name) { // man kan bruge både initials og name
        return employeeList.stream()
            .anyMatch(Employee -> 
                Employee.getInitials().equals(name) ||
                Employee.getName().equals(name)
            );
    }   
    
    public void logIn(String initials) {
        loggedIn = initials;
    }

    public void logOut() {
        loggedIn = null;
    }

    public String isLoggedIn() {
        return loggedIn;
    }

    public void showAllEmployees() {
        employeeView.printEmployeeList(model.getAllEmployees());
    }
}
