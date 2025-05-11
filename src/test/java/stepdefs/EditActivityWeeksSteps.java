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

public class EditActivityWeeksSteps {
    private AppModel model = new AppModel();
    private ActivityController controller;
    private Project project;
    private Activity activity;
    private Scanner scanner;

    @Given("a project {string} with activity {string} of duration {int} weeks exists")
    public void a_project_with_activity_of_duration_weeks_exists(String projectId,
                                                                 String activityId,
                                                                 int initialWeeks) {
        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025, 1, 4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(initialWeeks);
        project = new Project(projectId, "DemoProject", start, end, new ArrayList<>(), null);
        model.addProject(project);

        // last argument is endWeek
        activity = new Activity(activityId, "SomeActivity", 10, 1, initialWeeks);
        model.addActivityToProject(project, activity);
    }

    @When("the user edits weeks of activity {string} to {int}")
    public void the_user_edits_weeks_of_activity_to(String activityId, int newWeeks) {
        // provide both startWeek and new endWeek to satisfy prompts
        int startWeek = activity.getStartWeek();
        int newEndWeek = startWeek + newWeeks - 1;
        scanner = new Scanner(startWeek + "\n" + newEndWeek + "\n");
        controller = new ActivityController(
                scanner,
                model,
                new AppView(),
                new ProjectView(),
                new EmployeeView()
        );
        controller.changeWeeks(activity);
    }

    @Then("the activity weeks for {string} should be {int}")
    public void the_activity_weeks_for_should_be(String activityId, int expectedWeeks) {
        Activity updated = model.getActivityInProject(project, activityId);
        assertNotNull("Activity should still exist", updated);
        // endWeek reflects the total weeks (given startWeek=1)
        assertEquals(expectedWeeks, updated.getEndWeek());
    }
}