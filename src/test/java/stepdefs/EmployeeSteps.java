package stepdefs;

import static org.junit.Assert.*;

import io.cucumber.java.en.*;
import model.*;
import view.AppView;
import view.EmployeeView;


import java.util.*;

import controller.EmployeeController;
import controller.StatusHolder;

public class EmployeeSteps {
    private final AppModel model;
    // private AppView appView;
    // private EmployeeController employeeController;
    // private Employee employee;
    // private EmployeeView employeeView;
    // private Project project; // mangler at tiføje
    // private final List<Employee> employees = new ArrayList<>();
    

    public EmployeeSteps(AppModel model) {
        this.model = model;
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
        AppModel.setLoggedIn(model.getEmployeeByInitials(string));
    }

    @Then("an employee is logged in with initials {string}")
    public void anEmployeeIsLoggedInWithInitials(String string) {
        assertEquals(AppModel.getLoggedIn(), model.getEmployeeByInitials(string));
    }

    @Given("a user with initials {string} and name {string} is registered")
    public void a_user_with_initials_and_name_is_registered(String s, String s2) {
        Employee e = new Employee(s, s2, new ArrayList<>());
        model.addEmployee(e);
    }

    @Then("the error message {string} is given")
    public void the_error_message_is_given(String s) {
        assertEquals(s,StatusHolder.getStatus());
    }

    // @Then("the initials {string} no longer exists")
    // public void the_initials_no_longer_exists(String s) {
    //     // Write code here that turns the phrase above into concrete actions
    // }

    // @When("a user deletes employee initials {string}")
    // public void a_user_deletes_employee_initials(String s) {
    //     // Write code here that turns the phrase above into concrete actions
    // }






}