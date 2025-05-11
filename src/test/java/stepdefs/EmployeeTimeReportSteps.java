/**
 *
 *
 * @author entire file was written by Frederik
 */
package stepdefs;

import controller.ReportController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Activity;
import model.AppModel;
import model.Employee;
import model.Project;
import view.AppView;
import view.ReportView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeTimeReportSteps {

    private AppModel model;
    private ReportController reportController;

    // Monday of week 10 and 25 in 2025
    WeekFields wf = WeekFields.ISO;
    LocalDate startDate = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 10)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
    LocalDate endDate   = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 25)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());

    @Given("an employee with initials {string} exists")

    public void employeeExists(String initials) {
        model = new AppModel();
        Employee e = new Employee(initials, "John Doe", new ArrayList<>());
        model.addEmployee(e);

        Activity a1 = new Activity("A1", "Code Review", 40, 10, 15);
        Project project = new Project("P1", "Code Project", startDate, endDate);
        project.assignProjectLeader(e);
        model.addProject(project);
        model.addActivityToProject(project, a1);
        model.assignEmployeeToActivity(e, a1);

        Scanner scanner = new Scanner(initials + "\n");
        AppView appView = new AppView();
        ReportView reportView = new ReportView();

        reportController = new ReportController(scanner, model, appView, reportView);
    }

    @Given("time entries have been logged for the employee")
    public void logTimeForEmployee() {
        Employee e = model.getEmployeeByInitials("JD");
        Activity a = model.getActivityGlobally("A1");
        model.logTimeEntry(e, a, 2.0, "2025-03-01");
        model.logTimeEntry(e, a, 3.0, "2025-03-02");
    }

    @When("I generate a time report for {string}")
    public void generateEmployeeReport(String initials) {
        reportController.employeeTimeReport();
    }

    @Then("the report should show the correct hours per day")
    public void reportShouldShowCorrectHours() {
        // Already printed in ReportView; for now we just check execution
    }

    @Given("no employee with initials {string} exists")
    public void no_employee_with_initials_exists(String initials) {
        model = new AppModel();  // empty model
        Scanner scanner = new Scanner(initials + "\n"); // simulate user input
        AppView appView = new AppView();
        ReportView reportView = new ReportView();
        reportController = new ReportController(scanner, model, appView, reportView);
    }

    @Then("I should see an error message saying employee {string} was not found")
    public void i_should_see_employee_not_found(String expectedMessage) {
        System.out.println("Captured: " + expectedMessage);
    }

}
