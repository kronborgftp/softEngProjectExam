package controller;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.ActivityView;
import view.AppView;
import view.TimeEntryView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static org.mockito.Mockito.*;

class TimeEntryControllerTest {
    private TimeEntryController controller;
    private AppModel model;
    private AppView appView;
    private TimeEntryView timeEntryView;
    private ActivityView activityView;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        model         = new AppModel();
        appView       = mock(AppView.class);
        timeEntryView = mock(TimeEntryView.class);
        activityView  = mock(ActivityView.class);
        scanner       = mock(Scanner.class);

        controller = new TimeEntryController(
                scanner,
                model,
                appView,
                timeEntryView,
                activityView
        );
    }

    @Test
    void testLogTime_success() {
        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);
        AppModel.setLoggedIn(e);

        LocalDate today = LocalDate.now();
        Project p = new Project("P1", "Demo", today, today.plusWeeks(1));
        model.addProject(p);

        Activity a = new Activity("A1", "Test Activity", 8, /*startWeek*/1, /*endWeek*/2);
        model.addActivityToProject(p, a);
        e.assignActivity(a);  // now employee.getAssignedActivities() contains 'a'

        when(scanner.nextLine()).thenReturn("A1", "2.0");

        controller.logTime();

        verify(activityView).printAllActivities(e.getAssignedActivities());
        verify(appView).prompt("Activity ID");
        verify(appView).prompt("Hours (use 0.5 for half-hour precision)");
        verify(timeEntryView).printTimeLogged(any(TimeEntry.class));
    }

    @Test
    void testLogTime_notLoggedIn() {
        AppModel.setLoggedIn(null);

        controller.logTime();

        verify(timeEntryView).printError("No employee is currently logged in.");
        verifyNoMoreInteractions(activityView, appView, timeEntryView);
    }

    @Test
    void testLogTime_noAssignedActivities() {
        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);
        AppModel.setLoggedIn(e);

        controller.logTime();

        verify(timeEntryView).printError("No activities assigned to you.");
        verifyNoMoreInteractions(activityView, appView, timeEntryView);
    }

    @Test
    void testLogTime_activityNotFound() {
        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);
        AppModel.setLoggedIn(e);

        LocalDate today = LocalDate.now();
        Project p = new Project("P1", "Demo", today, today.plusWeeks(1));
        model.addProject(p);

        Activity a = new Activity("A1", "Test", 8, 1, 2);
        model.addActivityToProject(p, a);
        e.assignActivity(a);

        when(scanner.nextLine()).thenReturn("BAD_ID");

        controller.logTime();

        verify(activityView).printAllActivities(e.getAssignedActivities());
        verify(appView).prompt("Activity ID");
        verify(timeEntryView).printError("Activity not found or not assigned to you.");
    }

    @Test
    void testLogTime_badPrecision() {
        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        model.addEmployee(e);
        AppModel.setLoggedIn(e);

        LocalDate today = LocalDate.now();
        Project p = new Project("P1", "Demo", today, today.plusWeeks(1));
        model.addProject(p);

        Activity a = new Activity("A1", "Test", 8, 1, 2);
        model.addActivityToProject(p, a);
        e.assignActivity(a);

        when(scanner.nextLine()).thenReturn("A1", "1.25");

        controller.logTime();

        verify(activityView).printAllActivities(e.getAssignedActivities());
        verify(appView).prompt("Activity ID");
        verify(appView).prompt("Hours (use 0.5 for half-hour precision)");
        verify(timeEntryView).printError("Please enter hours with half-hour precision (e.g., 1.0, 1.5, etc.)");
    }
}
