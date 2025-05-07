package controller.edit;

import model.AppModel;
import model.Employee;
import model.Project;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Locale;

import java.util.Scanner;

public class ProjectEditor {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final ProjectView projectView;
    private final EmployeeView employeeView;

    public ProjectEditor(Scanner scanner, AppModel model, AppView appView,
                         ProjectView projectView, EmployeeView employeeView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.projectView = projectView;
        this.employeeView = employeeView;
    }

    public void edit(Project project) {
        boolean editing = true;
        while (editing) {
            appView.printEditMenu("project");
            switch (scanner.nextLine()) {
                case "1" -> changeName(project);
                case "2" -> changeWeeks(project);
                case "3" -> changeLeader(project);
                case "0" -> editing = false;
                default -> appView.printError("Invalid input.");
            }
        }
    }

    private void changeName(Project project) {
        appView.prompt("New Project Name");
        String name = scanner.nextLine();
        model.updateProjectName(project, name);
        projectView.printInfo("Project name updated.");
    }

    private void changeWeeks(Project project) {
        appView.prompt("New Start Year");
        int startYear = Integer.parseInt(scanner.nextLine());
        appView.prompt("New Start Week");
        int startWeek = Integer.parseInt(scanner.nextLine());
        appView.prompt("New End Year");
        int endYear = Integer.parseInt(scanner.nextLine());
        appView.prompt("New End Week");
        int endWeek = Integer.parseInt(scanner.nextLine());

        WeekFields wf = WeekFields.ISO;
        LocalDate startDate = LocalDate.of(startYear, 1, 4).with(wf.weekOfYear(), startWeek).with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate endDate   = LocalDate.of(endYear,   1, 4).with(wf.weekOfYear(), endWeek).with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());

        model.updateProjectDates(project, startDate, endDate);
        projectView.printInfo("Project duration updated.");
    }

    private void changeLeader(Project project) {
        appView.prompt("New Leader Initials");
        Employee leader = model.getEmployeeByInitials(scanner.nextLine());
        if (leader == null) {
            employeeView.printError("Employee not found.");
        } else {
            model.changeProjectLeader(project, leader);
            projectView.printInfo("Project leader updated.");
        }
    }
}

