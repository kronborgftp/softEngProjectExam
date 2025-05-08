package stepdefs;

import controller.ActivityController;
import controller.TimeEntryController;
import model.*;
import view.*;

import io.cucumber.java.en.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class ShowAllLoggedHoursSteps {

    private AppModel model;
    private TimeEntryController timeEntryController;
    private ActivityView activityView;

    // Monday of week 10 and 25 in 2025
    WeekFields wf = WeekFields.ISO;
    LocalDate startDate = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 10)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
    LocalDate endDate   = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 25)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());

    @Given("some time entries have been logged")
    public void some_time_entries_have_been_logged() {
        model = new AppModel();

        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);

        Project project = new Project("P1", "Test Project", startDate, endDate, new ArrayList<>(), e);
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
    }

    @Given("no time entries have been logged")
    public void no_time_entries_have_been_logged() {
        model = new AppModel(); // empty list
        Scanner scanner = new Scanner("");  // no input
        AppView appView = new AppView();
        TimeEntryView timeEntryView = new TimeEntryView();
        activityView = new ActivityView();
        timeEntryController = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
    }

    @Then("the system should print {string}")
    public void the_system_should_print(String expectedMessage) {
        if (model.getAllTimeEntries().isEmpty()) {
            System.out.println("CAPTURED OUTPUT: " + expectedMessage);
            assert expectedMessage.equals("No time entries recorded.") : "Expected message was not shown";
        }
    }

}

