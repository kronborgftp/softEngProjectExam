package stepdefs;

import static org.junit.Assert.*;
import java.util.*;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import model.*;
import view.AppView;
import view.EmployeeView;



import controller.StatusHolder;

public class EmployeeSteps {
    private final AppModel model;
    // private AppView appView;
    // private final EmployeeController employeeController;
    // private Employee employee;
    // private EmployeeView employeeView;
    // private Project project; // mangler at tiføje
    // private final List<Employee> employees = new ArrayList<>();
    

    public EmployeeSteps(AppModel model) {
        this.model = model;
    }

    @Before
    public void resetStatus() {
        StatusHolder.setStatus(null);
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

    @Then("the employee {string} is logged in with initials {string}")
    public void anEmployeeIsLoggedInWithInitials(String string, String string2) {
        assertEquals(model.getEmployeeByInitials(string2),AppModel.getLoggedIn());
    }

    @Given("an employee with initials {string} and name {string} is registered")
    public void an_employee_with_initials_and_name_is_registered(String s, String s2) {
        Employee e = new Employee(s, s2, new ArrayList<>());
        model.addEmployee(e);
    }

    @Then("the error message {string} is given")
    public void the_error_message_is_given(String s) {
        assertEquals(s,StatusHolder.getStatus());
    }

    @Then("the employee initials {string} is logged out")
    public void the_employee_initials_is_logged_out(String s) {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the employee logs out")
    public void the_employee_logs_out() {
        assertTrue(AppModel.getLoggedIn() == null);
    }

    @Given("an employee is logged in with initials {string}")
    public void an_employee_is_logged_in_with_initials(String s) {
        AppModel.setLoggedIn(model.getEmployeeByInitials(s));
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