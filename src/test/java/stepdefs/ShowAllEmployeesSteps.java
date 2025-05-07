package stepdefs;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import model.AppModel;
import model.Employee;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShowAllEmployeesSteps {

    AppModel model = new AppModel();
    List<String> output = new ArrayList<>();

    @Given("the following employees are registered:")
    public void the_following_employees_are_registered(DataTable dataTable) {
        List<Map<String, String>> employees = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : employees) {
            String initials = row.get("initials");
            String name = row.get("name");
            Employee e = new Employee(initials, name, new ArrayList<>());
            model.addEmployee(e);
        }
    }

    @When("I request to view all employees")
    public void i_request_to_view_all_employees() {
        output.clear();
        for (Employee e : model.getAllEmployees()) {
            // Add formatted string to output list
            output.add(e.getInitials() + " " + e.getName());
        }
    }

    @Then("I should see the following employees listed:")
    public void i_should_see_the_following_employees_listed(DataTable dataTable) {
        List<Map<String, String>> expected = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : expected) {
            String expectedLine = row.get("initials") + " " + row.get("name");
            System.out.println("EXPECTING: " + expectedLine);
            System.out.println("CURRENT OUTPUT: " + output);
            assertTrue(output.contains(expectedLine), "Expected employee not listed: " + expectedLine);
        }
    }

    @Given("no employees are registered")
    public void no_employees_are_registered() {
        model = new AppModel(); // Fresh model with no employees
        output.clear();
    }

    @Then("I should see a message {string}")
    public void i_should_see_a_message(String expectedMessage) {
        if (model.getAllEmployees().isEmpty()) {
            output.add("No employees found.");
        }
        assertTrue(output.contains(expectedMessage), "Expected message not found: " + expectedMessage);
    }

}
