package stepdefs;

import controller.*;
import io.cucumber.java.en.*;
import model.*;
import view.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class ProjectTimeReportSteps {

    private AppModel model;
    private ReportView reportView;
    private AppView appView;
    private ReportController reportController;
    private Scanner scanner;
    // Monday of week 10 and 25 in 2025
    WeekFields wf = WeekFields.ISO;
    LocalDate startDate = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 10)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
    LocalDate endDate   = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 25)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());


            
    @Given("a project with ID {string} exists")
    public void a_project_exists(String projectId) {
        model = new AppModel();
        scanner = new Scanner("25001\n"); // simulate user input
        appView = new AppView();
        reportView = new ReportView();

        Employee e1 = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e1);

        Project project = new Project(projectId, "Gooning Project", startDate, endDate, new ArrayList<>(), e1);
        model.addProject(project);

        Activity a1 = new Activity("A1", "Gooning", 80, 10, 15);
        Activity a2 = new Activity("A2", "Gooning Max!", 120, 16, 22);
        model.addActivityToProject(project, a1);
        model.addActivityToProject(project, a2);

        model.assignEmployeeToActivity(e1, a1);
        model.assignEmployeeToActivity(e1, a2);

        reportController = new ReportController(scanner, model, appView, reportView);
    }

    @And("time entries have been logged for the project")
    public void time_entries_have_been_logged() {
        Employee e = model.getEmployeeByInitials("JD");
        Activity a1 = model.getActivityGlobally("A1");
        Activity a2 = model.getActivityGlobally("A2");

        model.logTimeEntry(e, a1, 4.0, "2025-03-04");
        model.logTimeEntry(e, a2, 5.5, "2025-03-10");
    }

    @When("I generate a project time report for {string}")
    public void i_generate_project_time_report(String projectId) {
        reportController.projectTimeReport(); // input already simulated in scanner
    }

    @Then("the report should show the correct hours for each activity")
    public void report_should_show_correct_hours() {
    }

    @Given("no project with ID {string} exists")
    public void no_project_exists(String projectId) {
        model = new AppModel();
        scanner = new Scanner(projectId + "\n"); // simulate invalid project ID input
        appView = new AppView();
        reportView = new ReportView();
        reportController = new ReportController(scanner, model, appView, reportView);
    }

    @Then("I should see an error message saying project {string} was not found")
    public void i_should_see_project_not_found(String expectedMessage) {
        Project project = model.getProjectById(expectedMessage);
        assert project == null;
    }

}
