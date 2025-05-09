package controller;

import model.*;
import view.AppView;
import view.FixedActivityView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class FixedActivityController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final FixedActivityView view;

    private static final DateTimeFormatter DANISH = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            .withLocale(new Locale("da", "DK"));

    public FixedActivityController(Scanner scanner, AppModel model, AppView appView, FixedActivityView view) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.view = view;
    }

    public void logAbsence() {
        Employee employee = model.getLoggedIn();
        if (employee == null) {
            view.printError("No employee is currently logged in.");
            return;
        }

        view.printFixedActivities(model.getAllFixedActivities());

        appView.prompt("Absence ID");
        String id = scanner.nextLine().toUpperCase();
        FixedActivity fixed = model.getFixedActivity(id);

        if (fixed == null) {
            view.printError("Invalid absence type.");
            return;
        }

        appView.prompt("Start Date (DD-MM-YYYY)");
        String startStr = scanner.nextLine();
        appView.prompt("End Date (DD-MM-YYYY)");
        String endStr = scanner.nextLine();

        LocalDate start, end;
        try {
            start = LocalDate.parse(startStr, DANISH);
            end = LocalDate.parse(endStr, DANISH);
        } catch (Exception e) {
            view.printError("Invalid date format.");
            return;
        }

        if (end.isBefore(start)) {
            view.printError("End date cannot be before start date.");
            return;
        }

        //convert into fixed activity
        Activity absenceActivity = model.getOrCreateFixedActivity(id, fixed.getName());

        int count = 0;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (date.getDayOfWeek().getValue() <= 5) {
                TimeEntry absence = new TimeEntry(UUID.randomUUID().toString(), employee, absenceActivity, date.toString());
                model.addTimeEntry(absence);
                view.printTimeLogged(absence);
                count++;
            }
        }

        appView.printInfo("Logged " + count + " day(s) of " + fixed.getName() + ".");
    }
}
