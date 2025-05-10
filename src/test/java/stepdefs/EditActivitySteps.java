package stepdefs;

import controller.ActivityController;
import io.cucumber.java.en.*;
import model.*;
import view.AppView;
import view.ProjectView;
import view.EmployeeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class EditActivitySteps {
    private AppModel model;
    private ActivityController controller;
    private Project project;
    private Activity activity;

    @Given("a project {string} with activity {string} named {string} exists")
    public void a_project_with_activity_named_exists(String projectId,
                                                     String activityId,
                                                     String initialName) {
        // initialize model + project + activity
        model = new AppModel();
        project = new Project(
                projectId,
                "DemoProject",
                LocalDate.now(),
                LocalDate.now().plusWeeks(1),
                new ArrayList<>(),
                null
        );
        model.addProject(project);

        activity = new Activity(activityId, initialName, 10, 1, 2);
        model.addActivityToProject(project, activity);
    }

    @When("the user renames activity {string} to {string}")
    public void the_user_renames_activity_to(String activityId, String newName) {
        // feed the newName into the controller’s scanner
        Scanner scanner = new Scanner(newName + "\n");
        controller = new ActivityController(
                scanner,
                model,
                new AppView(),
                new ProjectView(),
                new EmployeeView()
        );
        // invoke the existing changeName(...) method
        controller.changeName(activity);
    }

    @Then("the activity name for {string} should be {string}")
    public void the_activity_name_for_should_be(String activityId, String expectedName) {
        Activity updated = model.getActivityInProject(project, activityId);
        assertNotNull("Activity should still exist", updated);
        assertEquals(expectedName, updated.getActivityName());
    }
}
