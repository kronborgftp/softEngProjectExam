package view;

import model.Employee;
import model.Activity;

import java.util.List;

public class EmployeeView {

    public void printEmployeeRegistered(Employee e) {
        System.out.println("Registered employee: " + e.getName() + " [" + e.getInitials() + "]");
    }

    public void printEmployeeLoggedIn(Employee e) {
        System.out.println("Employee logged in: " + e.getName() + " [" + e.getInitials() + "]");
    }

    public void printEmployeeList(List<Employee> employees) {
        System.out.println("\n--- All Employees ---");
        for (Employee e : employees) {
            System.out.println("• " + e.getName() + " [" + e.getInitials() + "]");
        }
    }

    public void printEmployeeAssignedToActivity(Employee e, Activity a) {
        System.out.println("Assigned " + e.getInitials() + " to activity " + a.getActivityId());
    }
    
    public void printError(String message) {
        System.out.println(message);
    }
}
