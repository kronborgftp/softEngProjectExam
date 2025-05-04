package controller;

import model.*;
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

    public TimeEntryController(Scanner scanner, AppModel model, AppView appView, TimeEntryView timeEntryView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.timeEntryView = timeEntryView;
    }

    public void logTime() {
        appView.prompt("Employee Initials");
        Employee employee = model.getEmployeeByInitials(scanner.nextLine());

        if (employee == null) {
            timeEntryView.printError("Employee not found.");
            return;
        }

        appView.prompt("Date (DD-MM-YYYY)");
        String date = scanner.nextLine();

        // Show activities the employee is assigned to
        List<Activity> assignedActivities = employee.getAssignedActivities();
        if (!assignedActivities.isEmpty()) {
            timeEntryView.printAssignedActivities(employee, assignedActivities);
        }

        appView.prompt("Activity ID (or type 'search' to find an activity)");
        String input = scanner.nextLine();

        Activity activity;

        if (input.equalsIgnoreCase("search")) {
            activity = searchActivity();
        } else {
            activity = model.getActivityGlobally(input);
        }

        if (activity == null) {
            timeEntryView.printError("Activity not found.");
            return;
        }

        appView.prompt("Hours (use 0.5 for half-hour precision, e.g., 1.5, 2.0, etc.)");
        double hours = Double.parseDouble(scanner.nextLine());

        // half-hour precision
        if (Math.round(hours * 2) != hours * 2) {
            timeEntryView.printError("Please enter hours with half-hour precision (e.g., 1.0, 1.5, 2.0, etc.)");
            return;
        }

        String timeEntryId = model.logTimeEntry(employee, activity, hours, date);
        timeEntryView.printTimeLogged(model.getTimeEntryById(timeEntryId));
    }

    private Activity searchActivity() {
        appView.prompt("Search by (1: Project ID, 2: Activity name)");
        String searchType = scanner.nextLine();

        switch (searchType) {
            case "1" -> {
                appView.prompt("Project ID");
                String projectId = scanner.nextLine();
                Project project = model.getProjectById(projectId);
                if (project == null) {
                    timeEntryView.printError("Project not found.");
                    return null;
                }
                timeEntryView.printActivitiesForProject(project);
                appView.prompt("Activity ID");
                return model.getActivityInProject(project, scanner.nextLine());
            }
            case "2" -> {
                appView.prompt("Activity name (partial match)");
                String activityName = scanner.nextLine().toLowerCase();
                List<Activity> matchingActivities = model.getAllActivities().stream()
                        .filter(a -> a.getActivityName().toLowerCase().contains(activityName))
                        .collect(Collectors.toList());

                if (matchingActivities.isEmpty()) {
                    timeEntryView.printError("No activities found matching that name.");
                    return null;
                }

                timeEntryView.printMatchingActivities(matchingActivities);
                appView.prompt("Select activity by number");
                int selection = Integer.parseInt(scanner.nextLine()) - 1;

                if (selection >= 0 && selection < matchingActivities.size()) {
                    return matchingActivities.get(selection);
                } else {
                    timeEntryView.printError("Invalid selection.");
                    return null;
                }
            }
            default -> {
                timeEntryView.printError("Invalid option.");
                return null;
            }
        }
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

        appView.prompt("Start Date (DD-MM-YYYY)");
        String startStr = scanner.nextLine();
        appView.prompt("End Date (DD-MM-YYYY)");
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