package stepdefs;

import controller.ActivityController;
import controller.TimeEntryController;
import model.*;
import view.*;

import io.cucumber.java.en.*;

import java.util.*;

public class ShowAllLoggedHoursSteps {

    private AppModel model;
    private TimeEntryController timeEntryController;
    private ActivityView activityView;

    @Given("some time entries have been logged")
    public void some_time_entries_have_been_logged() {
        model = new AppModel();

        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);

        Project project = new Project("P1", "Test Project", 2024, 2025, new ArrayList<>(), e);
        Activity a = new Activity("A1", "Dev Work", 10, 20, 40);
        model.addProject(project);
        model.addActivityToProject(project, a);
        model.assignEmployeeToActivity(e, a);

        model.logTimeEntry(e, a, 2.5, "2025-05-01");
        model.logTimeEntry(e, a, 3.0, "2025-05-02");

        Scanner scanner = new Scanner("");  // No input needed here
        AppView appView = new AppView();
        TimeEntryView timeEntryView = new TimeEntryView();
        timeEntryController = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
    }

    @When("I view all logged hours")
    public void i_view_all_logged_hours() {
        timeEntryController.showAllLoggedHours();  // just prints output
    }

    @Then("the system should print all logged time entries")
    public void the_system_should_print_all_logged_time_entries() {
        // For now, this is visual-only — you can add output capture later
    }
}

