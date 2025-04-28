package controller;

import model.AppModel;
import model.Employee;
import model.Project;
import model.Activity;
import view.AppView;
import view.EmployeeView;
import view.ProjectView;

import java.util.ArrayList;
import java.util.List;
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

    public void createProject() {
        appView.prompt("Project ID");
        String projectId = scanner.nextLine();
        appView.prompt("Project Name");
        String name = scanner.nextLine();
        appView.prompt("Start Week");
        int startWeek = Integer.parseInt(scanner.nextLine());
        appView.prompt("End Week");
        int endWeek = Integer.parseInt(scanner.nextLine());

        appView.prompt("Leader Initials");
        Employee leader = model.getEmployeeByInitials(scanner.nextLine());
        if (leader == null) {
            appView.printError("Leader not found. Project creation cancelled.");
            return;
        }

        Project project = new Project(projectId, name, startWeek, endWeek, new ArrayList<>(), leader);
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




}
