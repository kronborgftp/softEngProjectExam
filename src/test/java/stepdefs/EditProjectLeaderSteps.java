/**
 *
 *
 * @author entire file was written by Lasse
 */
package stepdefs;

import controller.ProjectController;
import io.cucumber.java.en.*;
import model.*;
import view.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class EditProjectLeaderSteps {
    private AppModel model;
    private ProjectController controller;
    private Project project;
    private Scanner scanner;

    private static class EmployeeViewSpy extends EmployeeView {
        private String lastError;
        @Override
        public void printError(String message) {
            lastError = message;
            super.printError(message);
        }
        public String getLastError() {
            return lastError;
        }
    }
    private final EmployeeViewSpy employeeView = new EmployeeViewSpy();
    private final AppView appView = new AppView();
    private final ProjectView projectView = new ProjectView();

    @Given("a project {string} with leader {string} exists")
    public void a_project_with_leader_exists(String projectId, String leaderInitials) {
        model = new AppModel();
        Employee original = new Employee(leaderInitials, "LeaderName", new ArrayList<>());
        model.addEmployee(original);

        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025,1,4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(1);

        project = new Project(projectId, "DemoProject", start, end);
        project.assignProjectLeader(original);
        model.addProject(project);
    }

    @Given("the system contains an employee with initials {string}")
    public void systemContainsEmployeeWithInitials(String initials) {
        model.addEmployee(new Employee(initials, "Imported", new ArrayList<>()));
    }

    @When("the user edits leader of project {string} to {string}")
    public void the_user_edits_leader_of_project_to(String projectId, String newInitials) {
        scanner = new Scanner(newInitials + "\n");
        controller = new ProjectController(
                scanner,
                model,
                appView,
                projectView,
                employeeView
        );
        controller.changeLeader(project);
    }

    @Then("the project leader for {string} should be {string}")
    public void the_project_leader_for_should_be(String projectId, String expected) {
        Project updated = model.getProjectById(projectId);
        assertNotNull(updated);
        assertNotNull(updated.getProjectLeader());
        assertEquals(expected, updated.getProjectLeader().getInitials());
    }

    @Then("I should see a leader-error message {string}")
    public void i_should_see_a_leader_error_message(String expected) {
        assertEquals(expected, employeeView.getLastError());
    }

    @Then("the project leader for {string} should still be {string}")
    public void the_project_leader_for_should_still_be(String projectId, String expected) {
        Project updated = model.getProjectById(projectId);
        assertNotNull(updated);
        assertEquals(expected, updated.getProjectLeader().getInitials());
    }
}