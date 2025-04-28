package controller;

import model.*;
import view.AppView;
import view.TimeEntryView;

import java.util.List;
import java.util.Scanner;

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

        appView.prompt("Activity ID");
        Activity activity = model.getActivityGlobally(scanner.nextLine());

        if (employee == null || activity == null) {
            timeEntryView.printError("Invalid employee or activity.");
            return;
        }

        appView.prompt("Hours");
        double hours = Double.parseDouble(scanner.nextLine());
        appView.prompt("Date");
        String date = scanner.nextLine();

        model.logTimeEntry(employee, activity, hours, date);
        timeEntryView.printTimeLogged(new TimeEntry("...", employee, activity, hours, date)); // feedback
    }

    private List<TimeEntry> getAllLoggedTime() {
        return model.getAllTimeEntries();
    }

    public void showAllLoggedHours() {
        timeEntryView.printAllLoggedTime(getAllLoggedTime());
    }

}
