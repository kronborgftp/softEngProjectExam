package stepdefs;

import controller.TimeEntryController;
import model.*;
import view.*;

import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.*;

public class LogTimeSteps {

    private AppModel model = new AppModel();
    private TimeEntryController controller;
    private Employee employee;
    private Activity activity;
    private Scanner scanner;

    @Given("an employee with initials {string} is registered and logged in")
    public void an_employee_is_registered_and_logged_in(String initials) {
        employee = new Employee(initials, "Test User", new ArrayList<>());
        model.addEmployee(employee);
        model.setLoggedIn(employee);
    }

    @And("a project with ID {string} and activity {string} is available")
    public void a_project_with_id_and_activity_is_available(String projectId, String activityId) {
        Project project = new Project(projectId, "Demo Project", LocalDate.now(), LocalDate.now().plusDays(30), new ArrayList<>(), employee);
        model.addProject(project);

        activity = new Activity(activityId, "Coding", 10, 10, 20);
        model.addActivityToProject(project, activity);
        model.assignEmployeeToActivity(employee, activity);
    }

    @When("the employee logs {double} hours on activity {string}")
    public void the_employee_logs_hours_on_activity(Double hours, String activityId) {
        scanner = new Scanner(String.join("\n", Arrays.asList(activityId, String.valueOf(hours))));
        controller = new TimeEntryController(scanner, model, new AppView(), new TimeEntryView(), new ActivityView());
        controller.logTime();
    }

    @Then("a time entry of {double} hours should exist for {string} on activity {string}")
    public void a_time_entry_should_exist_for_employee(Double hours, String initials, String activityId) {
        List<TimeEntry> entries = model.getAllTimeEntries();

        boolean found = entries.stream().anyMatch(entry ->
                entry.getEmployee().getInitials().equals(initials)
                        && entry.getActivity().getActivityId().equals(activityId)
                        && entry.getHours() == hours
                        && entry.getDate().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
        );

        assertTrue("Expected time entry not found", found);
    }

    @When("the employee tries to log {double} hours on invalid activity ID {string}")
    public void the_employee_tries_to_log_hours_on_invalid_activity(Double hours, String invalidActivityId) {
        scanner = new Scanner(String.join("\n", Arrays.asList(invalidActivityId, String.valueOf(hours))));
        controller = new TimeEntryController(scanner, model, new AppView(), new TimeEntryView(), new ActivityView());
        controller.logTime();
    }

    @When("the employee tries to log {string} hours on activity {string}")
    public void the_employee_tries_to_log_invalid_hour_format(String invalidHours, String activityId) {
        scanner = new Scanner(String.join("\n", Arrays.asList(activityId, invalidHours)));
        controller = new TimeEntryController(scanner, model, new AppView(), new TimeEntryView(), new ActivityView());

        try {
            controller.logTime();
        } catch (Exception e) {
            System.out.println("Handled invalid input: " + e.getMessage());
        }
    }

    @Then("no time entry should exist for employee {string}")
    public void no_time_entry_should_exist_for_employee(String initials) {
        long count = model.getAllTimeEntries().stream()
                .filter(e -> e.getEmployee().getInitials().equals(initials))
                .count();

        assertEquals(0, count);
    }



}
