/**
 *
 *
 * @author Frederik, Lasse and Kim
 */
package controller;

import model.Activity;
import model.AppModel;
import model.Employee;
import model.TimeEntry;
import view.ActivityView;
import view.AppView;
import view.TimeEntryView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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

    //Written by all three members.
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

    //Written by Lasse
    public void showAllLoggedHours() {
        timeEntryView.printAllLoggedTime(model.getAllTimeEntries());
    }


    //written by Frederik and Lasse refactored by Kim (Moved from other class)
    public void editTimeEntry(Employee employee) {
        List<TimeEntry> entries = model.getAllTimeEntries().stream()
                .filter(e -> e.getEmployee().getInitials().equalsIgnoreCase(employee.getInitials()))
                .toList();

        if (entries.isEmpty()) {
            timeEntryView.printError("No time entries found for this employee.");
            return;
        }

        timeEntryView.printTimeEntriesForEmployee(employee, entries);

        appView.prompt("Select entry number to edit (or 0 to cancel)");
        int sel;
        try {
            sel = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            timeEntryView.printError("Cancelled or invalid selection.");
            return;
        }
        if (sel <= 0 || sel > entries.size()) {
            timeEntryView.printError("Cancelled or invalid selection.");
            return;
        }

        TimeEntry entry = entries.get(sel - 1);
        if (entry.getHours() == -1) {
            timeEntryView.printError("This is an absence entry. Editing is not supported.");
            return;
        }

        appView.prompt("New hours (current: " + entry.getHours() + ")");
        String line = scanner.nextLine();
        double newHrs;
        try {
            newHrs = Double.parseDouble(line);
        } catch (NumberFormatException ex) {
            timeEntryView.printError("Invalid hours input.");
            return;
        }
        if (Math.round(newHrs * 2) != newHrs * 2) {
            timeEntryView.printError("Hours must be in half-hour increments.");
            return;
        }

        appView.prompt("New date (current: " + entry.getDate() + ")");
        String newDate = scanner.nextLine();

        model.updateTimeEntry(entry.getEntryID(), newHrs, newDate);
        timeEntryView.printTimeEntryUpdated();
    }
}