package hellocucumber;

import model.AppModel;
import model.Employee;
import model.Project;
import model.Activity;
import model.TimeEntry;
import view.AppView;
import view.EmployeeView;
import io.cucumber.java.en.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import controller.EmployeeController;

public class EmployeeSteps {
    private AppModel model;
    private AppView appView;
    private EmployeeController employeeController;
    private Employee employee;
    private EmployeeView employeeView;
    private Project project; // mangler at tiføje
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeSteps(
            AppModel appModel, 
            // AppView appView, 
            EmployeeController employeeController, 
            Employee employee
            // EmployeeView employeeView
            ) {
        this.model = appModel;
        // this.appView = appView;
        this.employeeController = employeeController;
        this.employee = employee;
        // this.employeeView = employeeView;
    }

    @When("The employee John Doe registers with initials {string} and name {string}")
    public void The_employee_John_Doe_registers_with_initials_and_name(String s, String s2) {
        Employee e = new Employee(s, s2, new ArrayList<>());
        model.addEmployee(e);
    }

    @Then("The employee John Doe has registered as employee with initials {string} and name {string}")
    public void The_employee_John_Doe_has_registered_as_employee_with_initials_and_name(String s, String s2) {
        assertTrue(model.hasEmployee(s));
    }

    @When("The employee John Doe logs in with initials {string}")
    public void The_employee_John_Doe_logs_in_with_initials(String s) {
        employeeController.setLoggedIn(model.getEmployeeByInitials(s));
    }

    @Then("The employee is logged in with initials {string}")
    public void The_employee_is_logged_in_with_initials(String s) {
        assertEquals(employeeController.getLoggedIn(), model.getEmployeeByInitials(s));
    }




}