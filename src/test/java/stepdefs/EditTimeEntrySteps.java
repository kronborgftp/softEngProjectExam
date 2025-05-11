package stepdefs;

import controller.TimeEntryController;
import io.cucumber.java.en.*;
import model.AppModel;
import model.Employee;
import model.Activity;
import model.Project;
import model.TimeEntry;
import view.AppView;
import view.TimeEntryView;
import view.ActivityView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class EditTimeEntrySteps {
    private AppModel model;
    private Employee employee;
    private Activity activity;
    private TimeEntryController controller;
    private Scanner scanner;
    private String entryId;

    @Given("an employee with initials {string} with activity {string} exists and is logged in")
    public void setupEmployeeAndActivity(String initials, String activityId) {
        model = new AppModel();
        employee = new Employee(initials, "Test User", new ArrayList<>());
        model.addEmployee(employee);
        AppModel.setLoggedIn(employee);

        // attach a dummy project so the activity has a parent
        Project p = new Project(
                "P1",
                "X",
                LocalDate.now(),
                LocalDate.now().plusWeeks(1)
        );
        p.setProjectLeader(employee);
        model.addProject(p);

        activity = new Activity(activityId, "TestActivity", 10, 1, 2);
        model.addActivityToProject(p, activity);
        model.assignEmployeeToActivity(employee, activity);
    }

    @Given("a time entry for activity {string} of {double} hours on {string} exists")
    public void createTimeEntry(String activityId, Double hours, String date) {
        entryId = model.logTimeEntry(employee, activity, hours, date);
    }

    @When("the user edits that entry to {double} hours on {string}")
    public void editThatEntry(Double newHours, String newDate) {
        // simulate: choose entry #1, then enter new hours, then new date
        scanner = new Scanner(String.join("\n",
                "1",
                newHours.toString(),
                newDate
        ) + "\n");

        controller = new TimeEntryController(
                scanner,
                model,
                new AppView(),
                new TimeEntryView(),
                new ActivityView()
        );
        controller.editTimeEntry(employee);
    }

    @Then("the time entry for activity {string} should be {double} hours on {string}")
    public void verifyEditedEntry(String activityId, Double expectedHours, String expectedDate) {
        TimeEntry updated = model.getTimeEntryById(entryId);
        assertNotNull("Entry must still exist", updated);
        assertEquals(expectedHours, updated.getHours(), 0.0);
        assertEquals(expectedDate, updated.getDate());
        assertEquals(activityId, updated.getActivity().getActivityId());
    }
}
