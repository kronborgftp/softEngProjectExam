/**
 *
 *
 * @author entire file was written by Lasse
 */
package stepdefs;

import controller.ProjectController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.AppModel;
import model.Project;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EditProjectNameSteps {
    private AppModel model = new AppModel();
    private Project project;
    private ProjectController controller;
    private Scanner scanner;

    @Given("a project {string} named {string} exists")
    public void a_project_named_exists(String projectId, String initialName) {
        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025, 1, 4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(4);

        project = new Project(projectId, initialName, start, end);
        model.addProject(project);
    }

    @When("the user edits project name of {string} to {string}")
    public void the_user_edits_project_name_of_to(String projectId, String newName) {
        scanner = new Scanner(newName + "\n");
        controller = new ProjectController(
                scanner,
                model,
                new AppView(),
                new ProjectView(),
                new EmployeeView()   // nu med EmployeeView som 5. parameter
        );
        controller.changeName(project);
    }

    @Then("the project name for {string} should be {string}")
    public void the_project_name_for_should_be(String projectId, String expectedName) {
        Project updated = model.getProjectById(projectId);
        assertNotNull("Projektet skal findes", updated);
        assertEquals("Projektets navn skal være ændret", expectedName, updated.getProjectName());
    }
}
