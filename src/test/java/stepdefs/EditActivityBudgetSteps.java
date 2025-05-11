/**
 *
 *
 * @author entire file was written by Lasse
 */
package stepdefs;

import controller.ActivityController;
import io.cucumber.java.en.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import model.AppModel;
import model.Project;
import model.Activity;
import view.AppView;
import view.ProjectView;
import view.EmployeeView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Scanner;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EditActivityBudgetSteps {
    private AppModel model;
    private ActivityController controller;
    private Project project;
    private Activity activity;
    private Scanner scanner;
    private AppView appView;
    private ProjectView projectView;
    private EmployeeView employeeView;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        model = new AppModel();
        appView = new AppView();
        projectView = new ProjectView();
        employeeView = new EmployeeView();
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Given("a project {string} with activity {string} with budget {int} hours exists")
    public void a_project_with_activity_with_budget_exists(String projectId, String activityId, int initialBudget) {
        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025, 1, 4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(1);
        project = new Project(projectId, "DemoProject", start, end);
        model.addProject(project);

        activity = new Activity(activityId, "SomeActivity", initialBudget, 1, 2);
        model.addActivityToProject(project, activity);
    }

    @When("the user edits budget of activity {string} to {int}")
    public void the_user_edits_budget_of_activity_to_int(String activityId, int newBudget) {
        scanner = new Scanner(newBudget + "\n");
        controller = new ActivityController(scanner, model, appView, projectView, employeeView);
        controller.changeBudget(activity);
    }

    @When("the user edits budget of activity {string} to {string}")
    public void the_user_edits_budget_of_activity_to_string(String activityId, String newBudgetStr) {
        scanner = new Scanner(newBudgetStr + "\n");
        controller = new ActivityController(scanner, model, appView, projectView, employeeView);
        controller.changeBudget(activity);
    }

    @Then("the activity budget for {string} should be {int}")
    public void the_activity_budget_for_should_be(String activityId, int expectedBudget) {
        Activity updated = model.getActivityInProject(project, activityId);
        assertNotNull("Activity should still exist", updated);
        assertEquals(expectedBudget, updated.getBudgetedHours());
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expected) {
        String actual = outContent.toString().trim();
        // normalize hyphen characters to ASCII hyphen
        actual = actual.replace('‐', '-');
        assertTrue(
                "Expected error message to contain \"" + expected + "\" but was \"" + actual + "\"",
                actual.contains(expected)
        );
    }
}
