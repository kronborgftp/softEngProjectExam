/**
 *
 *
 * @author Frederik and Kim
 */
package view;

import model.Employee;

import java.util.List;

public class EmployeeView {

    //Written by Frederik
    public void printEmployeeRegistered(Employee e) {
        System.out.println("Registered employee: " + e.getName() + " [" + e.getInitials() + "]");
    }

    //Written by Kim
    public void printEmployeeLoggedIn(Employee e) {
        System.out.println("Employee logged in: " + e.getName() + " [" + e.getInitials() + "]");
    }

    //Written by Frederik
    public void printEmployeeList(List<Employee> employees) {
        System.out.println("\n--- All Employees ---");
        for (Employee e : employees) {
            System.out.println("• " + e.getName() + " [" + e.getInitials() + "]");
        }
    }

    //Written by Frederik
    public void printError(String message) {
        System.out.println(message);
    }
}
