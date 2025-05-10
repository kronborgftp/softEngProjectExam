package stepdefs;

import static org.junit.Assert.*;

import io.cucumber.java.en.*;
import model.*;
import controller.*;
import view.AppView;
import view.FixedActivityView;

import java.util.*;

public class LogAbsenceSteps {
    private final AppModel model = new AppModel();
    private Scanner scanner;
    private Employee employee;
    private final AppView appView = new AppView();
    private final FixedActivityView view = new FixedActivityView();

    @Given("an employee with initials {string} is registered")
    public void an_employee_with_initials_is_registered(String initials) {
        employee = new Employee(initials, "John Doe", new ArrayList<>());
        model.addEmployee(employee);
    }

    @And("the employee is logged in")
    public void the_employee_is_logged_in() {
        model.setLoggedIn(employee);
    }

    @When("the employee logs an absence of type {string} from {string} to {string}")
    public void the_employee_logs_an_absence_of_type_from_to(String type, String startDate, String endDate) {
        scanner = new Scanner(String.join("\n", Arrays.asList(
                typeToNumber(type),  // Reverted to "1", "2", "3"
                startDate,
                endDate
        )));
        FixedActivityController fixedActivityController = new FixedActivityController(scanner, model, appView, view);
        fixedActivityController.logAbsence();
    }

    @Then("{int} vacation absence entries should be created for employee {string}")
    public void vacation_absence_entries_should_be_created_for_employee(Integer expectedCount, String initials) {
        long count = model.getAllTimeEntries().stream()
                .filter(e -> e.getEmployee().getInitials().equals(initials))
                .filter(e -> e.getActivity().getActivityId().equals("VAC"))
                .count();
        assertEquals((long) expectedCount, count);
    }

    @When("no one is logged in")
    public void no_one_is_logged_in() {
        model.setLoggedIn(null);
    }

    @When("someone tries to log a {string} absence from {string} to {string}")
    public void someone_tries_to_log_absence_without_login(String type, String startDate, String endDate) {
        scanner = new Scanner(String.join("\n", Arrays.asList(
                typeToNumber(type),
                startDate,
                endDate
        )));
        FixedActivityController fixedActivityController = new FixedActivityController(scanner, model, appView, view);
        fixedActivityController.logAbsence();
    }

    @Then("no absence entries should be created for employee {string}")
    public void no_absence_entries_should_be_created_for_employee(String initials) {
        long count = model.getAllTimeEntries().stream()
                .filter(e -> e.getEmployee().getInitials().equals(initials))
                .count();
        assertEquals(0, count);
    }

    @When("the employee tries to log an absence with invalid type {string} from {string} to {string}")
    public void the_employee_tries_to_log_an_absence_with_invalid_type(String invalidType, String startDate, String endDate) {
        scanner = new Scanner(String.join("\n", Arrays.asList(
                invalidType,  // Still valid for test: invalid type = "4" or "XYZ"
                startDate,
                endDate
        )));
        FixedActivityController fixedActivityController = new FixedActivityController(scanner, model, appView, view);
        fixedActivityController.logAbsence();
    }

    @When("the employee tries to log a {string} absence from {string} to {string}")
    public void the_employee_tries_to_log_a_absence_from_to_with_invalid_dates(String type, String startDate, String endDate) {
        scanner = new Scanner(String.join("\n", Arrays.asList(
                typeToNumber(type),
                startDate,
                endDate
        )));
        FixedActivityController fixedActivityController = new FixedActivityController(scanner, model, appView, view);
        fixedActivityController.logAbsence();
    }


    // Helper method to map labels to input numbers
    private String typeToNumber(String label) {
        return switch (label.toLowerCase()) {
            case "vacation" -> "1";
            case "sick leave" -> "2";
            case "course" -> "3";
            default -> label;  // allow invalid input for error tests
        };
    }
}
