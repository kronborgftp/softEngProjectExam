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
import java.util.*;           // <-- for Scanner, List, ArrayList, etc.
import static org.junit.Assert.*;

public class EditActivityNameSteps {
    private AppModel model = new AppModel();
    private ActivityController controller;
    private Project project;
    private Activity activity;
    private Scanner scanner;

    private static class ProjectViewSpy extends ProjectView {
        private String lastError;
        @Override public void printError(String message) {
            this.lastError = message;
            super.printError(message);
        }
        public String getLastError() {
            return lastError;
        }
    }

    private ProjectViewSpy projectView = new ProjectViewSpy();
    private AppView appView = new AppView();
    private EmployeeView employeeView = new EmployeeView();

    @Given("a project {string} with activity {string} named {string} exists")
    public void a_project_with_activity_named_exists(String pid, String aid, String name) {
        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025,1,4)
                .with(wf.weekOfYear(),1)
                .with(wf.dayOfWeek(),DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(1);

        project = new Project(pid, "Demo", start, end);
        model.addProject(project);

        activity = new Activity(aid, name, 10, 1, 1);
        model.addActivityToProject(project, activity);
    }

    @When("the user edits name of activity {string} to {string}")
    public void the_user_edits_name_of_activity_to(String aid, String newName) {
        scanner = new Scanner(newName + "\n");
        controller = new ActivityController(
                scanner, model, appView, projectView, employeeView
        );
        controller.changeName(activity);
    }

    @Then("the activity name for {string} should be {string}")
    public void the_activity_name_for_should_be(String aid, String expected) {
        Activity updated = model.getActivityInProject(project, aid);
        assertNotNull(updated);
        assertEquals(expected, updated.getActivityName());
    }

    @Then("I should see a name‐error message {string}")
    public void i_should_see_a_name_error_message(String expected) {
        assertEquals(expected, projectView.getLastError());
    }
}

