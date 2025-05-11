/**
 *
 *
 * @author entire file was written by Frederik
 */
package stepdefs;

import controller.TimeEntryController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.*;
import view.ActivityView;
import view.AppView;
import view.TimeEntryView;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShowAllLoggedHoursSteps {

    private AppModel model;
    private TimeEntryController timeEntryController;
    private ActivityView activityView = new ActivityView();
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    private final WeekFields wf = WeekFields.ISO;
    private final LocalDate startDate = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 10)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
    private final LocalDate endDate = LocalDate.of(2025, 1, 4)
            .with(wf.weekOfYear(), 25)
            .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());

    @Given("some work time entries have been logged")
    public void some_work_time_entries_have_been_logged() {
        model = new AppModel();

        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);

        Project project = new Project("P1", "Test Project", startDate, endDate);
        project.assignProjectLeader(e);
        Activity a = new Activity("A1", "Dev Work", 10, 20, 40);
        model.addProject(project);
        model.addActivityToProject(project, a);
        model.assignEmployeeToActivity(e, a);

        model.logTimeEntry(e, a, 2.5, "01-05-2025");
        model.logTimeEntry(e, a, 3.0, "02-05-2025");

        setupOutputCapture();

        Scanner scanner = new Scanner("");
        AppView appView = new AppView();
        TimeEntryView timeEntryView = new TimeEntryView();
        timeEntryController = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
    }

    @Given("some absence entries have been logged")
    public void some_absence_entries_have_been_logged() {
        model = new AppModel();

        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);

        Activity vac = model.getOrCreateFixedActivity("VAC", "Vacation");
        TimeEntry absence1 = new TimeEntry(UUID.randomUUID().toString(), e, vac, "03-05-2025");
        TimeEntry absence2 = new TimeEntry(UUID.randomUUID().toString(), e, vac, "04-05-2025");

        model.addTimeEntry(absence1);
        model.addTimeEntry(absence2);

        setupOutputCapture();

        Scanner scanner = new Scanner("");
        AppView appView = new AppView();
        TimeEntryView timeEntryView = new TimeEntryView();
        timeEntryController = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
    }

    @Given("no time entries have been logged")
    public void no_time_entries_have_been_logged() {
        model = new AppModel();

        setupOutputCapture();

        Scanner scanner = new Scanner("");
        AppView appView = new AppView();
        TimeEntryView timeEntryView = new TimeEntryView();
        timeEntryController = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
    }

    @Given("some work and absence time entries have been logged")
    public void some_work_and_absence_time_entries_have_been_logged() {
        model = new AppModel();

        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);
        AppModel.setLoggedIn(e);

        // Work entry
        Project project = new Project("P1", "Test Project", startDate, endDate);
        project.assignProjectLeader(e);
        Activity work = new Activity("A1", "Dev Work", 10, 20, 40);
        model.addProject(project);
        model.addActivityToProject(project, work);
        model.assignEmployeeToActivity(e, work);
        model.logTimeEntry(e, work, 4.0, "05-05-2025");

        // Absence entry
        Activity vac = model.getOrCreateFixedActivity("VAC", "Vacation");
        TimeEntry absence = new TimeEntry(UUID.randomUUID().toString(), e, vac, "06-05-2025");
        model.addTimeEntry(absence);

        setupOutputCapture();

        Scanner scanner = new Scanner("");
        AppView appView = new AppView();
        TimeEntryView timeEntryView = new TimeEntryView();
        timeEntryController = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
    }


    @When("I view all logged hours")
    public void i_view_all_logged_hours() {
        timeEntryController.showAllLoggedHours();
        restoreOutput(); // optional
    }

    @Then("the system should print all logged work and absence entries")
    public void the_system_should_print_all_logged_work_and_absence_entries() {
        String output = outputStream.toString();
        assertTrue(output.contains("Dev Work"));
        assertTrue(output.contains("Vacation"));
        assertTrue(output.contains("05-05-2025"));
        assertTrue(output.contains("06-05-2025"));
        assertTrue(output.contains("4,0"));
        assertTrue(output.contains("ABSENT"));
    }


    @Then("the system should print all logged work time entries")
    public void the_system_should_print_all_logged_work_time_entries() {
        String output = outputStream.toString();
        assertTrue(output.contains("Dev Work"));
        assertTrue(output.contains("01-05-2025"));
        assertTrue(output.contains("02-05-2025"));
        assertTrue(output.contains("2,5"));
        assertTrue(output.contains("3,0"));
        assertFalse(output.contains("ABSENT")); // Ensure no absence is printed
    }

    @Then("the system should print all logged absence entries")
    public void the_system_should_print_all_logged_absence_entries() {
        String output = outputStream.toString();
        assertTrue(output.contains("Vacation"));
        assertTrue(output.contains("03-05-2025"));
        assertTrue(output.contains("04-05-2025"));
        assertTrue(output.contains("ABSENT"));
        assertFalse(output.contains("Dev Work")); // Ensure no work is printed
    }

    @Then("the system should print {string}")
    public void the_system_should_print(String expectedMessage) {
        String output = outputStream.toString().trim();
        assertTrue("Expected output to contain message: " + expectedMessage,
                output.contains(expectedMessage));
    }

    private void setupOutputCapture() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private void restoreOutput() {
        System.setOut(originalOut);
    }
}
