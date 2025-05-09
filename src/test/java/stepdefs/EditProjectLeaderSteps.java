package stepdefs;

import controller.ProjectController;
import io.cucumber.java.en.*;
import model.*;
import view.AppView;
import view.ProjectView;
import view.EmployeeView;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.*;

import static org.junit.Assert.*;

public class EditProjectLeaderSteps {
    private AppModel model;
    private ProjectController controller;
    private Project project;
    private Scanner scanner;

    @Given("a project {string} with leader {string} exists")
    public void a_project_with_leader_exists(String projectId, String leaderInitials) {
        model = new AppModel();
        // create and register the original leader
        Employee original = new Employee(leaderInitials, "Name", new ArrayList<>());
        model.addEmployee(original);

        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025,1,4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(1);

        project = new Project(projectId, "Demo", start, end, new ArrayList<>(), original);
        model.addProject(project);
    }

    @When("the user edits leader of project {string} to {string}")
    public void the_user_edits_leader_of_project_to(String projectId, String newLeaderInitials) {
        // register the new leader in the model
        Employee newLeader = new Employee(newLeaderInitials, "New Name", new ArrayList<>());
        model.addEmployee(newLeader);

        scanner = new Scanner(newLeaderInitials + "\n");
        controller = new ProjectController(
                scanner,
                model,
                new AppView(),
                new ProjectView(),
                new EmployeeView()
        );

        controller.changeLeader(project);
    }

    @Then("the project leader for {string} should be {string}")
    public void the_project_leader_for_should_be(String projectId, String expectedInitials) {
        Project updated = model.getProjectById(projectId);
        assertNotNull("Project must still exist", updated);
        assertNotNull("Leader must be set", updated.getProjectLeader());
        assertEquals(expectedInitials, updated.getProjectLeader().getInitials());
    }
}
