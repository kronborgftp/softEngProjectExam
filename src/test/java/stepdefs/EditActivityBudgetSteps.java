package stepdefs;

import controller.ActivityController;
import io.cucumber.java.en.*;
import model.AppModel;
import model.Project;
import model.Activity;
import view.AppView;
import view.ProjectView;
import view.EmployeeView;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.*;

import static org.junit.Assert.*;

public class EditActivityBudgetSteps {
    private AppModel model = new AppModel();
    private ActivityController controller;
    private Project project;
    private Activity activity;
    private Scanner scanner;

    @Given("a project {string} with activity {string} with budget {int} hours exists")
    public void a_project_with_activity_with_budget_hours_exists(String projectId,
                                                                 String activityId,
                                                                 int initialBudget) {
        // pick a dummy week span
        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025, 1, 4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(1);

        project = new Project(projectId, "DemoProject", start, end, new ArrayList<>(), null);
        model.addProject(project);

        activity = new Activity(activityId, "SomeActivity", initialBudget, 1, 2);
        model.addActivityToProject(project, activity);
    }

    @When("the user edits budget of activity {string} to {int}")
    public void the_user_edits_budget_of_activity_to(String activityId, int newBudget) {
        // controller will prompt once for the new budget
        scanner = new Scanner(newBudget + "\n");
        controller = new ActivityController(
                scanner,
                model,
                new AppView(),
                new ProjectView(),
                new EmployeeView()
        );
        controller.changeBudget(activity);
    }

    @Then("the activity budget for {string} should be {int}")
    public void the_activity_budget_for_should_be(String activityId, int expectedBudget) {
        Activity updated = model.getActivityInProject(project, activityId);
        assertNotNull("Activity should still exist", updated);
        assertEquals(expectedBudget, updated.getBudgetedHours());
    }
}
