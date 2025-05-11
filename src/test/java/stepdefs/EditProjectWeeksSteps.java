/**
 *
 *
 * @author entire file was written by Lasse
 */
package stepdefs;

import controller.ProjectController;
import io.cucumber.java.en.*;
import model.AppModel;
import model.Project;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Scanner;

import static org.junit.Assert.*;

public class EditProjectWeeksSteps {
    private AppModel model;
    private ProjectController controller;
    private Project project;
    private AppView appView;
    private ProjectViewSpy projectView;
    private EmployeeView employeeView;
    private Scanner scanner;

    // Spy for at fange printError
    private static class ProjectViewSpy extends ProjectView {
        private String lastError;
        @Override
        public void printError(String message) {
            lastError = message;
        }
        public String getLastError() {
            return lastError;
        }
    }

    @Given("a project {string} with duration {int} weeks exists")
    public void a_project_with_duration_weeks_exists(String projectId, Integer initialWeeks) {
        model        = new AppModel();
        appView      = new AppView();
        projectView  = new ProjectViewSpy();
        employeeView = new EmployeeView();

        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025, 1, 4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end = start.plusWeeks(initialWeeks);

        project = new Project(projectId, "DemoProj", start, end);
        model.addProject(project);
    }

    @When("the user edits weeks of project {string} to {int}")
    public void the_user_edits_weeks_of_project_to_int(String projectId, Integer newWeeks) {
        WeekFields wf = WeekFields.ISO;
        int startWeek = project.getStartDate().get(wf.weekOfYear());
        int year      = project.getStartDate().getYear();
        int endWeek   = startWeek + newWeeks;

        String input = String.join("\n",
                String.valueOf(year),
                String.valueOf(startWeek),
                String.valueOf(year),
                String.valueOf(endWeek)
        ) + "\n";

        scanner    = new Scanner(input);
        controller = new ProjectController(scanner, model, appView, projectView, employeeView);
        controller.changeWeeks(project);
    }

    @When("the user edits weeks of project {string} to {string}")
    public void the_user_edits_weeks_of_project_to_string(String projectId, String newWeeks) {
        WeekFields wf  = WeekFields.ISO;
        int startWeek = project.getStartDate().get(wf.weekOfYear());
        int year      = project.getStartDate().getYear();

        String input = String.join("\n",
                String.valueOf(year),
                String.valueOf(startWeek),
                String.valueOf(year),
                newWeeks
        ) + "\n";

        scanner    = new Scanner(input);
        controller = new ProjectController(scanner, model, appView, projectView, employeeView);
        controller.changeWeeks(project);
    }

    @When("the user edits project {string} start week to {int} and end week to {int}")
    public void the_user_edits_project_start_and_end(String projectId, Integer startWeek, Integer endWeek) {
        int year = project.getStartDate().getYear();
        String input = String.join("\n",
                String.valueOf(year),
                String.valueOf(startWeek),
                String.valueOf(year),
                String.valueOf(endWeek)
        ) + "\n";

        scanner    = new Scanner(input);
        controller = new ProjectController(scanner, model, appView, projectView, employeeView);
        controller.changeWeeks(project);
    }

    @Then("I should see a project weeks error message {string}")
    public void i_should_see_a_project_weeks_error_message(String expected) {
        assertEquals(expected, projectView.getLastError());
    }

    @Then("the project duration for {string} should be {int} weeks")
    public void the_project_duration_for_should_be_weeks(String projectId, Integer expectedWeeks) {
        Project updated = model.getProjectById(projectId);
        assertNotNull("Project must exist", updated);

        long daysBetween =
                updated.getEndDate().toEpochDay()
                        - updated.getStartDate().toEpochDay();

        int actualWeeks = (int)(daysBetween / 7);

        assertEquals(expectedWeeks.intValue(), actualWeeks);
    }

}
