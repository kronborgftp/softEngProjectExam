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

    @When("an employee registers with initials {string} and name {string}")
    public void anEmployeeRegistersWithInitialsAndName(String string, String string2) {
        Employee e = new Employee(string, string2, new ArrayList<>());
        model.addEmployee(e);
    }
    
    @Then("an employee has registered as employee with initials {string} and name {string}")
    public void anEmployeeHasRegisteredAsEmployeeWithInitialsAndName(String string, String string2) {
        assertTrue(model.hasEmployee(string));    
    }

    @When("an employee logs in with initials {string}")
    public void anEmployeeLogsInWithInitials(String string) {
        model.setLoggedIn(model.getEmployeeByInitials(string));
    }

    @Then("an employee is logged in with initials {string}")
    public void anEmployeeIsLoggedInWithInitials(String string) {
        assertEquals(model.getLoggedIn(), model.getEmployeeByInitials(string));
    }





}