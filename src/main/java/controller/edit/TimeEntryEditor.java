package controller.edit;

import model.AppModel;
import model.Employee;
import model.TimeEntry;
import view.AppView;
import view.TimeEntryView;

import java.util.List;
import java.util.Scanner;

public class TimeEntryEditor {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final TimeEntryView timeEntryView;

    public TimeEntryEditor(Scanner scanner, AppModel model, AppView appView, TimeEntryView timeEntryView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.timeEntryView = timeEntryView;
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
