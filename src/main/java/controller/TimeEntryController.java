package controller;

import model.*;
import view.ActivityView;
import view.AppView;
import view.TimeEntryView;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TimeEntryController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final TimeEntryView timeEntryView;
    private final ActivityView activityView;


    public TimeEntryController(Scanner scanner, AppModel model, AppView appView, TimeEntryView timeEntryView, ActivityView activityView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.timeEntryView = timeEntryView;
        this.activityView = activityView;
    }

    public void logTime() {
        appView.prompt("Employee Initials");
        Employee employee = model.getEmployeeByInitials(scanner.nextLine());

        if (employee == null) {
            timeEntryView.printError("Employee not found.");
            return;
        }

        appView.prompt("Date (YYYY-MM-DD)");
        String date = scanner.nextLine();

        // Show all activities (assigned + others)
        List<Activity> allActivities = model.getAllActivities();
        activityView.printAllActivities(allActivities);


        appView.prompt("Activity ID");
        String activityId = scanner.nextLine();
        Activity activity = model.getActivityGlobally(activityId);

        if (activity == null) {
            timeEntryView.printError("Activity not found.");
            return;
        }

        appView.prompt("Hours (use 0.5 for half-hour precision)");
        double hours = Double.parseDouble(scanner.nextLine());

        if (Math.round(hours * 2) != hours * 2) {
            timeEntryView.printError("Please enter hours with half-hour precision (e.g., 1.0, 1.5, etc.)");
            return;
        }

        String timeEntryId = model.logTimeEntry(employee, activity, hours, date);
        timeEntryView.printTimeLogged(model.getTimeEntryById(timeEntryId));
    }

    public void logAbsence() {
        appView.prompt("Employee Initials");
        String initials = scanner.nextLine();
        Employee employee = model.getEmployeeByInitials(initials);

        if (employee == null) {
            timeEntryView.printError("Employee not found.");
            return;
        }

        appView.prompt("Absence Type (1: Vacation, 2: Sick Leave, 3: Course)");
        String input = scanner.nextLine();
        String id, name;
        switch (input) {
            case "1" -> { id = "VAC"; name = "Vacation"; }
            case "2" -> { id = "SICK"; name = "Sick Leave"; }
            case "3" -> { id = "COURSE"; name = "Course"; }
            default -> {
                timeEntryView.printError("Invalid choice.");
                return;
            }
        }

        Activity activity = model.getOrCreateStandardActivity(id, name);

        appView.prompt("Start Date (YYYY-MM-DD)");
        String startStr = scanner.nextLine();
        appView.prompt("End Date (YYYY-MM-DD)");
        String endStr = scanner.nextLine();

        LocalDate start, end;
        try {
            start = LocalDate.parse(startStr);
            end = LocalDate.parse(endStr);
        } catch (Exception e) {
            timeEntryView.printError("Invalid date format.");
            return;
        }

        if (end.isBefore(start)) {
            timeEntryView.printError("End date cannot be before start date.");
            return;
        }

        int count = 0;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (date.getDayOfWeek().getValue() <= 5) {
                TimeEntry absence = new TimeEntry(UUID.randomUUID().toString(), employee, activity, date.toString());
                model.addTimeEntry(absence);
                timeEntryView.printTimeLogged(absence);
                count++;
            }
        }

        appView.printInfo("Logged " + count + " day(s) of absence (" + name + ").");
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
}