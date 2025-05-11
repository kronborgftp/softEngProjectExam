/**
 *
 *
 * @author entire file was written by Lasse
 */
package stepdefs;

import controller.TimeEntryController;
import io.cucumber.java.en.*;
import model.*;
import view.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.*;

public class EditTimeEntrySteps {
    private AppModel model;
    private Employee employee;
    private Activity activity;
    private String entryId;
    private TimeEntryController controller;
    private Scanner scanner;
    private AppView appView;
    private ActivityView activityView;
    private TimeEntryViewSpy timeEntryView;

    private static final DateTimeFormatter DANISH = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withLocale(new Locale("da", "DK"));

    private static class TimeEntryViewSpy extends TimeEntryView {
        private String lastError;
        @Override
        public void printError(String message) {
            lastError = message;
        }
        public String getLastError() {
            return lastError;
        }
    }

    @Given("an employee with initials {string} with activity {string} exists and is logged in")
    public void setupEmployeeAndActivity(String initials, String activityId) {
        model = new AppModel();
        employee = new Employee(initials, "Test User", new ArrayList<>());
        model.addEmployee(employee);
        AppModel.setLoggedIn(employee);

        Project p = new Project("P1","X",
                LocalDate.now(), LocalDate.now().plusWeeks(1)
        );
        p.setProjectLeader(employee);
        model.addProject(p);

        activity = new Activity(activityId, "TestActivity", 10, 1, 2);
        model.addActivityToProject(p, activity);
        model.assignEmployeeToActivity(employee, activity);

        appView = new AppView();
        activityView = new ActivityView();
        timeEntryView = new TimeEntryViewSpy();
    }

    @Given("a time entry for activity {string} of {double} hours on {string} exists")
    public void createTimeEntry(String aid, Double hours, String date) {
        String iso = LocalDate.parse(date, DANISH).toString();
        entryId = model.logTimeEntry(employee, activity, hours, iso);
    }

    @Given("an absence entry for activity {string} exists")
    public void createAbsenceEntry(String aid) {
        String today = LocalDate.now().toString();
        entryId = model.logTimeEntry(employee, activity, -1.0, today);
    }

    @When("the user edits that entry to {double} hours on {string}")
    public void editThatEntry(Double hrs, String date) {
        String iso = LocalDate.parse(date, DANISH).toString();
        scanner = new Scanner(String.join("\n","1", hrs.toString(), iso) + "\n");
        controller = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
        controller.editTimeEntry(employee);
    }

    @When("the user edits that entry to {string} hours on {string}")
    public void editThatEntryNonNumeric(String hrs, String date) {
        String iso = LocalDate.parse(date, DANISH).toString();
        scanner = new Scanner(String.join("\n","1", hrs, iso) + "\n");
        controller = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
        controller.editTimeEntry(employee);
    }

    @When("the user selects entry number {int} to edit")
    public void selectEntryNumber(int sel) {
        scanner = new Scanner(sel + "\n");
        controller = new TimeEntryController(scanner, model, appView, timeEntryView, activityView);
        controller.editTimeEntry(employee);
    }

    @Then("the time entry for activity {string} should be {double} hours on {string}")
    public void verifyEditedEntry(String aid, Double expHrs, String date) {
        TimeEntry t = model.getTimeEntryById(entryId);
        assertNotNull(t);
        assertEquals(expHrs, t.getHours(), 0.0);
        assertEquals(date, LocalDate.parse(t.getDate()).format(DANISH));
        assertEquals(aid, t.getActivity().getActivityId());
    }

    @Then("I should see a time entry error message {string}")
    public void i_should_see_time_entry_error(String expected) {
        assertEquals(expected, timeEntryView.getLastError());
    }
}
