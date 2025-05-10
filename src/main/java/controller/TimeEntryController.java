package controller;

import model.*;
import view.ActivityView;
import view.AppView;
import view.TimeEntryView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TimeEntryController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final TimeEntryView timeEntryView;
    private final ActivityView activityView;
    private static final DateTimeFormatter DANISH = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(new Locale("da", "DK"));

    public TimeEntryController(Scanner scanner, AppModel model, AppView appView, TimeEntryView timeEntryView, ActivityView activityView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.timeEntryView = timeEntryView;
        this.activityView = activityView;
    }

    public void logTime() {
        Employee employee = AppModel.getLoggedIn();
        if (employee == null) {
            timeEntryView.printError("No employee is currently logged in.");
            return;
        }

        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DANISH);

        List<Activity> assignedActivities = employee.getAssignedActivities();
        if (assignedActivities.isEmpty()) {
            timeEntryView.printError("No activities assigned to you.");
            return;
        }

        activityView.printAllActivities(assignedActivities);

        appView.prompt("Activity ID");
        String activityId = scanner.nextLine();
        Activity activity = model.getActivityGlobally(activityId);

        if (activity == null || !assignedActivities.contains(activity)) {
            timeEntryView.printError("Activity not found or not assigned to you.");
            return;
        }

        appView.prompt("Hours (use 0.5 for half-hour precision)");
        double hours = Double.parseDouble(scanner.nextLine());

        if (Math.round(hours * 2) != hours * 2) {
            timeEntryView.printError("Please enter hours with half-hour precision (e.g., 1.0, 1.5, etc.)");
            return;
        }

        String timeEntryId = model.logTimeEntry(employee, activity, hours, formattedDate);
        timeEntryView.printTimeLogged(model.getTimeEntryById(timeEntryId));
    }

    public void showAllLoggedHours() {
        timeEntryView.printAllLoggedTime(model.getAllTimeEntries());
    }

    public void showEmployeeLoggedHours() {
        appView.prompt("Employee Initials");
        Employee employee = model.getEmployeeByInitials(scanner.nextLine());

        if (employee == null) {
            timeEntryView.printError("Employee not found.");
            return;
        }

        List<TimeEntry> employeeEntries = model.getAllTimeEntries().stream()
                .filter(entry -> entry.getEmployee().getInitials().equals(employee.getInitials()))
                .collect(Collectors.toList());

        timeEntryView.printTimeEntriesForEmployee(employee, employeeEntries);
    }
    
    public void editTimeEntry() {
        appView.prompt("Employee Initials");
        Employee employee = model.getEmployeeByInitials(scanner.nextLine());

        if (employee == null) {
            timeEntryView.printError("Employee not found.");
            return;
        }

        List<TimeEntry> entries = model.getAllTimeEntries().stream()
                .filter(e -> e.getEmployee().getInitials().equalsIgnoreCase(employee.getInitials()))
                .toList();

        if (entries.isEmpty()) {
            timeEntryView.printError("No time entries found for this employee.");
            return;
        }

        timeEntryView.printTimeEntriesForEmployee(employee, entries);

        appView.prompt("Select entry number to edit (or 0 to cancel)");
        int selection = Integer.parseInt(scanner.nextLine());

        if (selection <= 0 || selection > entries.size()) {
            timeEntryView.printError("Cancelled or invalid selection.");
            return;
        }

        TimeEntry entry = entries.get(selection - 1);

        if (entry.getHours() == -1) {
            timeEntryView.printError("This is an absence entry. Editing is not supported.");
            return;
        }

        appView.prompt("New hours (current: " + entry.getHours() + ")");
        double newHours = Double.parseDouble(scanner.nextLine());
        if (Math.round(newHours * 2) != newHours * 2) {
            timeEntryView.printError("Hours must be in half-hour increments.");
            return;
        }

        appView.prompt("New date (current: " + entry.getDate() + ")");
        String newDate = scanner.nextLine();

        model.updateTimeEntry(entry.getEntryID(), newHours, newDate);
        timeEntryView.printTimeEntryUpdated();
    }
}