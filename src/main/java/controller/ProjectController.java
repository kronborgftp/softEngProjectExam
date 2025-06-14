/**
 *
 *
 * @author Frederik, Lasse and Kim
 */
package controller;

import model.AppModel;
import model.Employee;
import model.Project;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Scanner;

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

    //Written by all three members.
    public void createProject() {
        // LocalDate objects year + week
        WeekFields wf = WeekFields.ISO;
        LocalDate startDate;
        LocalDate endDate;

        int startYear = LocalDate.now().getYear();

        String yearIDNo = String.valueOf(startYear).substring(2,4);
        String noOfProjects = String.valueOf(model.getProjectCountByYear(yearIDNo) + 1);

        String projectId = yearIDNo + lastIDNumber(noOfProjects);

        appView.prompt("Project Name");
        String name = scanner.nextLine();
        appView.prompt("Start Week");
        int startWeek = Integer.parseInt(scanner.nextLine());
        appView.prompt("Expected End Year");
        int endYear = Integer.parseInt(scanner.nextLine());
        appView.prompt("Expected End Week");
        int endWeek = Integer.parseInt(scanner.nextLine());

        try {
            startDate = LocalDate.of(startYear, 1, 4)
                    .with(wf.weekOfYear(), startWeek)
                    .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
            endDate   = LocalDate.of(endYear, 1, 4)
                    .with(wf.weekOfYear(), endWeek)
                    .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        } catch (DateTimeException ex) {
            StatusHolder.setStatus("Invalid year or week number");
            projectView.printError("Invalid year or week number");
            return;
        }

        // Start before end
        if (endDate.isBefore(startDate)) {
            StatusHolder.setStatus("End week cannot be before start week");
            projectView.printError("End week (" + endWeek + "/" + endYear +
                    ") cannot be before start week (" +
                    startWeek + "/" + startYear + ").");
            return;
        }

        // appView.prompt("Leader Initials");
        // Employee leader = model.getEmployeeByInitials(scanner.nextLine());
        // if (leader == null) {
        //     appView.printError("Leader not found. Project creation cancelled.");
        //     return;
        // }

        Project project = new Project(projectId, name, startDate, endDate);
        // project.assignProjectLeader(leader);
        model.addProject(project);
        projectView.printProjectCreated(project);
    }

    //Written by Kim
    public String lastIDNumber(String noOfProjectsByYear) {
        String IDNo = "";
        for (int i = 0; i < 3 - noOfProjectsByYear.length(); i++) {
            IDNo = IDNo + "0";
        }
        return IDNo + noOfProjectsByYear;
    }

    //Written by Lasse
    public void showAllProjects() {
        projectView.printProjectList(model.getAllProjects());
    }

    //written by Frederik and Lasse refactored by Kim (Moved from other class)
    public void changeName(Project project) {
        appView.prompt("New Project Name");
        String newName = scanner.nextLine();

        if (newName == null || newName.trim().isEmpty()) {
            projectView.printError("Invalid name input.");
            return;
        }

        if (newName.equals(project.getProjectName())) {
            projectView.printError("Name must be different.");
            return;
        }

        model.updateProjectName(project, newName);
        projectView.printInfo("Project name updated.");
    }

    //written by Frederik and Lasse refactored by Kim (Moved from other class)
    public void changeWeeks(Project project) {
        appView.prompt("New Start Year");
        appView.prompt("New Start Week");
        appView.prompt("New End Year");
        appView.prompt("New End Week");

        int startYear, startWeek, endYear, endWeek;
        try {
            startYear = Integer.parseInt(scanner.nextLine());
            startWeek = Integer.parseInt(scanner.nextLine());
            endYear   = Integer.parseInt(scanner.nextLine());
            endWeek   = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            projectView.printError("Invalid weeks input.");
            return;
        }

        int delta = endWeek - startWeek;
        if (delta < 0) {
            projectView.printError("Weeks must be positive.");
            return;
        }
        if (delta == 0) {
            projectView.printError("End week must be after start week.");
            return;
        }

        LocalDate originalStart = project.getStartDate();
        LocalDate newEnd        = originalStart.plusWeeks(delta);

        model.updateProjectDates(project, originalStart, newEnd);
        projectView.printInfo("Project duration updated.");
    }

    //written by Frederik and Lasse refactored by Kim (Moved from other class)
    public void changeLeader(Project project) {
        appView.prompt("New Leader Initials");
        String input = scanner.nextLine();

        // reject empty
        if (input == null || input.trim().isEmpty()) {
            employeeView.printError("Invalid initials.");
            return;
        }

        Employee leader = model.getEmployeeByInitials(input);
        if (leader == null) {
            employeeView.printError("Employee not found.");
        } else {
            project.assignProjectLeader(leader);
            projectView.printInfo("Project leader updated.");
        }
    }
}
