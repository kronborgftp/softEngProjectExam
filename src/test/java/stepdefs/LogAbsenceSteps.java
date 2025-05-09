package stepdefs;

import static org.junit.Assert.*;

import io.cucumber.java.en.*;
import model.*;
import controller.*;
import view.ActivityView;
import view.AppView;
import view.TimeEntryView;

import java.time.LocalDate;
import java.util.*;

public class LogAbsenceSteps {
    private AppModel model = new AppModel();
    private TimeEntryController timeEntryController;
    private Scanner scanner;
    private Employee employee;
    private List<String> inputs = new ArrayList<>();

    @Given("an employee with initials {string} is registered")
    public void an_employee_with_initials_is_registered(String initials) {
        employee = new Employee(initials, "John Doe", new ArrayList<>());
        model.addEmployee(employee);
    }

    @When("the employee logs an absence of type {string} from {string} to {string}")
    public void the_employee_logs_an_absence_of_type_from_to(String type, String startDate, String endDate) {
        // Map type to input number
        Map<String, String> typeMap = Map.of(
                "Vacation", "1",
                "Sick Leave", "2",
                "Course", "3"
        );
        scanner = new Scanner(String.join("\n", Arrays.asList(
                employee.getInitials(),
                typeMap.get(type),
                startDate,
                endDate
        )));
        timeEntryController = new TimeEntryController(scanner, model, new AppView(), new TimeEntryView(), new ActivityView());
        // timeEntryController.logAbsence();
    }

    @Then("{int} vacation absence entries should be created for employee {string}")
    public void vacation_absence_entries_should_be_created_for_employee(Integer expectedCount, String initials) {
        List<TimeEntry> entries = model.getAllTimeEntries();
        long count = entries.stream()
                .filter(e -> e.getEmployee().getInitials().equals(initials))
                .filter(e -> e.getActivity().getActivityId().equals("VAC"))
                .count();
        assertEquals((long) expectedCount, count);
    }

    @Given("no employee is registered with initials {string}")
    public void no_employee_is_registered_with_initials(String initials) {
        // Ensure the employee is not in the model
        assertNull(model.getEmployeeByInitials(initials));
    }

    @When("someone tries to log a {string} absence from {string} to {string} using initials {string}")
    public void someone_tries_to_log_a_absence_from_to_using_initials(String type, String startDate, String endDate, String initials) {
        Map<String, String> typeMap = Map.of(
                "Vacation", "1",
                "Sick Leave", "2",
                "Course", "3"
        );
        scanner = new Scanner(String.join("\n", Arrays.asList(
                initials,
                typeMap.get(type),
                startDate,
                endDate
        )));
        timeEntryController = new TimeEntryController(scanner, model, new AppView(), new TimeEntryView(), new ActivityView());
        // timeEntryController.logAbsence();
    }

    @Then("no absence entries should exist for employee {string}")
    public void no_absence_entries_should_exist_for_employee(String initials) {
        List<TimeEntry> entries = model.getAllTimeEntries();
        long count = entries.stream()
                .filter(e -> e.getEmployee().getInitials().equals(initials))
                .count();
        assertEquals(0, count);
    }

    @When("the employee tries to log an absence with invalid type {string} from {string} to {string}")
    public void the_employee_tries_to_log_an_absence_with_invalid_type_from_to(String invalidType, String startDate, String endDate) {
        scanner = new Scanner(String.join("\n", Arrays.asList(
                employee.getInitials(),
                invalidType,
                startDate,
                endDate
        )));
        timeEntryController = new TimeEntryController(scanner, model, new AppView(), new TimeEntryView(), new ActivityView());
        // timeEntryController.logAbsence();
    }

    @When("the employee tries to log a {string} absence from {string} to {string}")
    public void the_employee_tries_to_log_a_absence_from_to_with_invalid_dates(String type, String startDate, String endDate) {
        Map<String, String> typeMap = Map.of(
                "Vacation", "1",
                "Sick Leave", "2",
                "Course", "3"
        );
        scanner = new Scanner(String.join("\n", Arrays.asList(
                employee.getInitials(),
                typeMap.get(type),
                startDate,
                endDate
        )));
        timeEntryController = new TimeEntryController(scanner, model, new AppView(), new TimeEntryView(), new ActivityView());
        // timeEntryController.logAbsence();
    }


    @Then("no absence entries should be created for employee {string}")
    public void no_absence_entries_should_be_created_for_employee(String initials) {
        List<TimeEntry> entries = model.getAllTimeEntries();
        long count = entries.stream()
                .filter(e -> e.getEmployee().getInitials().equals(initials))
                .count();
        assertEquals(0, count);
    }



}

