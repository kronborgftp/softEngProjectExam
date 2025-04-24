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

    public void showAllEmployees() {
        employeeView.printEmployeeList(model.getAllEmployees());
    }
}
