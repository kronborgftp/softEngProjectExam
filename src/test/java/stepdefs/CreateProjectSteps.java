package stepdefs;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Scanner;

import controller.EmployeeController;
import io.cucumber.java.en.*;
import model.AppModel;
import model.Employee;
import model.Project;
import controller.ProjectController;
// import net.bytebuddy.asm.Advice.Local;
import view.*;

import java.time.LocalDate;

public class CreateProjectSteps { // this file was written by kim
    private AppModel model;
    private ProjectController projectController;

    @Given("the system is ready for project creation")
    public void the_system_is_ready_for_project_creation() {
        model = new AppModel();
        projectController = new ProjectController(new Scanner(System.in), model, new AppView(), new ProjectView(), new EmployeeView());
    }

    @When("an employee creates project with ID {string} name {string} start and end date {string} and {string}")
    public void an_employee_creates_project_with_ID_name_start_and_end_date_and(String s, String s2, String s3, String s4) {
        Project p = new Project(s, s2, LocalDate.parse(s3), LocalDate.parse(s4));
        model.addProject(p);
    }

    @Then("a project with ID {string} name {string} start and end date {string} and {string} is created")
    public void a_project_with_ID_name_start_and_end_date_and_is_created(String s, String s2, String s3, String s4) {
        Project p = model.getAllProjects().stream()
            .filter(project -> project.getProjectID().equalsIgnoreCase(s))
            .findFirst()
            .orElse(null);
        assertEquals(p.getProjectID(),s);
        assertEquals(p.getProjectName(),s2);
        assertEquals(p.getStartDate(),LocalDate.parse(s3));
        assertEquals(p.getEndDate(),LocalDate.parse(s4));
    }

    @Given("a project with ID {string} and name {string} exists")
    public void a_project_with_ID_and_name_exists(String s, String s2) {
        Project p = new Project(s, s2, LocalDate.now(), LocalDate.now());
        model.addProject(p);
    }


    // @Given("a project with ID {string} and name {string} exists")
    // public void a_project_with_name_exists(String s, String s2) {
    //     // Write code here that turns the phrase above into concrete actions
    // }


}
