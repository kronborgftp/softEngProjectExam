package stepdefs;

import static org.junit.Assert.*;
import java.util.ArrayList;

import io.cucumber.java.en.*;
import model.AppModel;
import model.Employee;
import model.Project;
import net.bytebuddy.asm.Advice.Local;

import java.time.LocalDate;

public class CreateProjectSteps {
    private final AppModel model;

    public CreateProjectSteps(AppModel model) {
        this.model = model;
    }

    @When("an employee creates project with ID {string} name {string} start and end date {string} and {string}")
    public void an_employee_creates_project_with_ID_name_start_and_end_date_and(String s, String s2, String s3, String s4) {
        Project p = new Project("P1", s2, LocalDate.parse(s3), LocalDate.parse(s4));
        model.addProject(p);
    }

    @Then("a project with ID {string} name {string} start and end date {string} and {string} is created")
    public void a_project_with_ID_name_start_and_end_date_and_is_created(String s, String s2, String s3, String s4) {
        assertEquals(model.getProjectById(s),new Project("P1", s2, LocalDate.parse(s3), LocalDate.parse(s4)));
    }

    @Given("a project with ID {string} and name {string} exists")
    public void a_project_with_name_exists(String s, String s2) {
        // Write code here that turns the phrase above into concrete actions
    }


}
