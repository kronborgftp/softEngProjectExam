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
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EditProjectWeeksSteps {
    private AppModel model;
    private ProjectController controller;
    private Project project;
    private AppView appView;
    private ProjectView projectView;
    private EmployeeView employeeView;
    private java.util.Scanner scanner;

    @Given("a project {string} with duration {int} weeks exists")
    public void a_project_with_duration_weeks_exists(String projectId, Integer initialWeeks) {
        model = new AppModel();
        appView = new AppView();
        projectView = new ProjectView();
        employeeView = new EmployeeView();

        // build a project starting week 1 of 2025, lasting initialWeeks
        WeekFields wf = WeekFields.ISO;
        LocalDate start = LocalDate.of(2025,1,4)
                .with(wf.weekOfYear(), 1)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate end   = start.plusWeeks(initialWeeks);
        project = new Project(projectId, "DemoProj", start, end);
        model.addProject(project);
    }

    @When("the user edits weeks of project {string} to {int}")
    public void the_user_edits_weeks_of_project_to(String projectId, Integer newWeeks) {
        // simulate entering: startYear, startWeek, endYear, endWeek
        // keep the same start (2025, week 1), only change end week
        String input =
                String.join("\n",
                        "2025",           // new start year
                        "1",              // new start week
                        "2025",           // new end year
                        newWeeks.toString() // new end week
                ) + "\n";
        scanner = new java.util.Scanner(input);

        controller = new ProjectController(
                scanner,
                model,
                appView,
                projectView,
                employeeView
        );
        controller.changeWeeks(project);
    }

    @Then("the project duration for {string} should be {int} weeks")
    public void the_project_duration_for_should_be_weeks(String projectId, Integer expectedWeeks) {
        Project updated = model.getProjectById(projectId);
        assertNotNull("Project must exist", updated);

        long intervals = ChronoUnit.WEEKS.between(
                updated.getStartDate(),
                updated.getEndDate()
        );
        long actualWeeks = intervals + 1;   // include both the starting and ending week
        assertEquals((long)expectedWeeks, actualWeeks);
    }
}
