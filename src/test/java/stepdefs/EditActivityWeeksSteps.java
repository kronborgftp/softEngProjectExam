/**
 *
 *
 * @author entire file was written by Lasse
 */
package stepdefs;

import controller.ActivityController;
import io.cucumber.java.en.*;
import model.*;
import view.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

import static org.junit.Assert.*;

public class EditActivityWeeksSteps {
    private AppModel model = new AppModel();
    private ActivityController controller;
    private Project project;
    private Activity activity;
    private Scanner scanner;

    // spy to capture errors
    private static class ProjectViewSpy extends ProjectView {
        private String lastError;
        @Override public void printError(String msg) {
            lastError = msg;
            super.printError(msg);
        }
        public String getLastError() { return lastError; }
    }

    private ProjectViewSpy projectView = new ProjectViewSpy();
    private AppView appView = new AppView();
    private EmployeeView employeeView = new EmployeeView();

    @Given("a project {string} with activity {string} of duration {int} weeks exists")
    public void a_project_with_activity_of_duration_weeks_exists(String pid,
                                                                 String aid,
                                                                 int initialWeeks) {
        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025,1,4)
                .with(wf.weekOfYear(),1)
                .with(wf.dayOfWeek(),DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(initialWeeks);

        project = new Project(pid, "Demo", start, end);
        model.addProject(project);

        activity = new Activity(aid, "SomeActivity", 10, 1, initialWeeks);
        model.addActivityToProject(project, activity);
    }

    @When("the user edits weeks of activity {string} to {int}")
    public void the_user_edits_weeks_of_activity_to(String aid, int totalWeeks) {
        // compute start + end from current startWeek=1
        int startWeek = activity.getStartWeek();
        int endWeek = startWeek + totalWeeks - 1;
        scanner = new Scanner(startWeek + "\n" + endWeek + "\n");
        controller = new ActivityController(
                scanner, model, appView, projectView, employeeView
        );
        controller.changeWeeks(activity);
    }

    @When("the user edits weeks of activity {string} to {string}")
    public void the_user_edits_weeks_of_activity_to_non_numeric(String aid, String bogus) {
        // still need two lines: startWeek + bogus
        int startWeek = activity.getStartWeek();
        scanner = new Scanner(startWeek + "\n" + bogus + "\n");
        controller = new ActivityController(
                scanner, model, appView, projectView, employeeView
        );
        controller.changeWeeks(activity);
    }

    @Then("the activity weeks for {string} should be {int}")
    public void the_activity_weeks_for_should_be(String aid, int expectedWeeks) {
        Activity updated = model.getActivityInProject(project, aid);
        assertNotNull(updated);
        // initial start=1 → endWeek = start + weeks – 1
        int actualWeeks = updated.getEndWeek() - updated.getStartWeek() + 1;
        assertEquals(expectedWeeks, actualWeeks);
    }

    @Then("I should see a weeks-error message {string}")
    public void i_should_see_a_weeks_error_message(String expected) {
        assertEquals(expected, projectView.getLastError());
    }
}
