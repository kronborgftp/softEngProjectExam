package stepdefs;

import static org.junit.Assert.*;
import java.util.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import model.*;
import controller.EmployeeController;
import view.AppView;
import view.EmployeeView;

import controller.StatusHolder;

public class EmployeeSteps { // this file was written by kim
    private AppModel model;
    // private AppView appView;
    private EmployeeController employeeController;
    // private Employee employee;
    // private EmployeeView employeeView;
    // private Project project; // mangler at tiføje
    // private final List<Employee> employees = new ArrayList<>();
    

    // public EmployeeSteps(AppModel model, EmployeeController employeeController) {

    //     this.model = model;
    //     this.employeeController = employeeController;
    // }

    @Before
    public void initialize_model_controller() {
        model = new AppModel();
        employeeController = new EmployeeController(new Scanner(System.in), model, new AppView(), new EmployeeView());
    }

    // @Given("model and employee controller are initialized")
    // public void the_system_is_ready_for_employee_registration() {
    //     model = new AppModel();
    //     employeeController = new EmployeeController(new Scanner(System.in), model, new AppView(), new EmployeeView());
    // }

    @When("an employee registers with initials {string} and name {string}")
    public void anEmployeeRegistersWithInitialsAndName(String string, String string2) {
        // Employee e = new Employee(string, string2, new ArrayList<>());
        // model.addEmployee(e);
        employeeController.registerEmployee(string, string2);
    }

    @Then("an employee has registered as employee with initials {string} and name {string}")
    public void anEmployeeHasRegisteredAsEmployeeWithInitialsAndName(String string, String string2) {
        assertTrue(model.hasEmployee(string));    
    }

    @When("an employee logs in with initials {string}")
    public void anEmployeeLogsInWithInitials(String string) {
        // AppModel.setLoggedIn(model.getEmployeeByInitials(string));
        employeeController.logIn(string);
    }

    @Then("the employee {string} is logged in with initials {string}")
    public void anEmployeeIsLoggedInWithInitials(String string, String string2) {
        assertEquals(model.getEmployeeByInitials(string2),AppModel.getLoggedIn());
    }

    @Given("an employee with initials {string} and name {string} is registered")
    public void an_employee_with_initials_and_name_is_registered(String s, String s2) {
        // Employee e = new Employee(s, s2, new ArrayList<>());
        // model.addEmployee(e);
        employeeController.registerEmployee(s, s2);
        
    }

    @Then("the error message {string} is given")
    public void the_error_message_is_given(String s) {
        assertEquals(s,StatusHolder.getStatus());
    }

    @When("the employee logs out")
    public void the_employee_logs_out() {
        AppModel.setLoggedIn(null);
    }

    @Then("the employee initials {string} is logged out")
    public void the_employee_initials_is_logged_out(String s) {
        assertTrue(AppModel.getLoggedIn() != model.getEmployeeByInitials(s));
    }

    @Given("an employee is logged in with initials {string}")
    public void an_employee_is_logged_in_with_initials(String s) {
        // AppModel.setLoggedIn(model.getEmployeeByInitials(s));
        employeeController.logIn(s);
    }






}