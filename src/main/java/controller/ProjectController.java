package controller;

import model.AppModel;
import model.Employee;
import model.Project;
import model.Activity;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;

public class ProjectController {
    private final Scanner scanner;
    private final AppModel model;
    private final AppView appView;
    private final ProjectView projectView;
    private final EmployeeView employeeView;
    private Project editProject; // nuværende valgte project at edit, så man ikke bhøver at indtaste det for hver kommando


    public ProjectController(Scanner scanner, AppModel model, AppView appView,
                          ProjectView projectView, EmployeeView employeeView) {
        this.scanner = scanner;
        this.model = model;
        this.appView = appView;
        this.projectView = projectView;
        this.employeeView = employeeView;
    }

    public void createProject() {
        appView.prompt("Project ID");
        String projectId = scanner.nextLine();
        appView.prompt("Project Name");
        String name = scanner.nextLine();
        appView.prompt("Start Year");
        int startYear = Integer.parseInt(scanner.nextLine());
        appView.prompt("Start Week");
        int startWeek = Integer.parseInt(scanner.nextLine());
        appView.prompt("End Year");
        int endYear = Integer.parseInt(scanner.nextLine());
        appView.prompt("End Week");
        int endWeek = Integer.parseInt(scanner.nextLine());

        // LocalDate objects year + week
        WeekFields wf = WeekFields.ISO;
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.of(startYear, 1, 4)
                    .with(wf.weekOfYear(), startWeek)
                    .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
            endDate   = LocalDate.of(endYear, 1, 4)
                    .with(wf.weekOfYear(), endWeek)
                    .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        } catch (DateTimeException ex) {
            projectView.printError("Invalid year or week number");
            return;
        }

        // Start before end
        if (endDate.isBefore(startDate)) {
            projectView.printError("End week (" + endWeek + "/" + endYear +
                    ") cannot be before start week (" +
                    startWeek + "/" + startYear + ").");
            return;
        }

        appView.prompt("Leader Initials");
        Employee leader = model.getEmployeeByInitials(scanner.nextLine());
        if (leader == null) {
            appView.printError("Leader not found. Project creation cancelled.");
            return;
        }

        Project project = new Project(projectId, name, startDate, endDate, new ArrayList<>(), leader);
        model.addProject(project);
        projectView.printProjectCreated(project);
    }

    public void showAllProjects() {
        projectView.printProjectList(model.getAllProjects());
    }

    public void editProject(Project p) {
        editProject = p;
    }

    public void addActivity(Activity a) {
        editProject.addActivity(a);
    }

    public void changeName(Project project) {
        appView.prompt("New Project Name");
        String name = scanner.nextLine();
        model.updateProjectName(project, name);
        projectView.printInfo("Project name updated.");
    }

    public void changeWeeks(Project project) {
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

    public void changeLeader(Project project) {
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
