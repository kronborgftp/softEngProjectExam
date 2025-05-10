package stepdefs;

import controller.AppController;
import model.*;
import static org.junit.Assert.*;

import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.util.*;

public class AssignActivitySteps {

    private final AppController app = new AppController();
    private final AppModel model = app.getModel();

    private Project currentProject;
    private Activity currentActivity;

    @Given("a project with name {string} exists")
    public void a_project_with_name_exists(String projectName) {
        Project p = new Project("P1", projectName,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31),
                new ArrayList<>(), null);
        model.addProject(p);
        this.currentProject = p;
    }

    @Given("a user with initials {string} and name {string} is registered")
    public void a_user_with_initials_and_name_is_registered(String initials, String name) {
        Employee e = new Employee(initials, name, new ArrayList<>());
        model.addEmployee(e);
    }

    @When("a user creates activity for {string} to project {string}")
    public void a_user_creates_activity_for_to_project(String activityName, String projectName) {
        Project p = model.getAllProjects().stream()
                .filter(proj -> proj.getProjectName().equals(projectName))
                .findFirst().orElse(null);
        assertNotNull(p);

        Activity a = new Activity("A1", activityName, 40, 1, 10);
        model.addActivityToProject(p, a);
        this.currentActivity = a;
    }

    @Then("the activity {string} is created for project {string}")
    public void the_activity_is_created_for_project(String activityName, String projectName) {
        Project p = model.getAllProjects().stream()
                .filter(proj -> proj.getProjectName().equals(projectName))
                .findFirst().orElse(null);
        assertNotNull(p);

        boolean exists = p.getActivityList().stream()
                .anyMatch(a -> a.getActivityName().equalsIgnoreCase(activityName));
        assertTrue(exists);
    }

    @Given("an activity {string} is assigned to project {string}")
    public void an_activity_is_assigned_to_project(String activityName, String projectName) {
        Project p = model.getAllProjects().stream()
                .filter(proj -> proj.getProjectName().equals(projectName))
                .findFirst().orElse(null);
        assertNotNull(p);

        Activity a = new Activity("A2", activityName, 30, 2, 8);
        model.addActivityToProject(p, a);
        this.currentActivity = a;
    }

    @When("employee {string} is assigned to the activity")
    public void employee_is_assigned_to_the_activity(String initials) {
        Employee e = model.getEmployeeByInitials(initials);
        assertNotNull(e);
        assertNotNull(currentActivity);

        model.assignEmployeeToActivity(e, currentActivity);
    }

    @Then("the activity has employee {string} assigned to it")
    public void the_activity_has_employee_assigned_to_it(String initials) {
        boolean assigned = currentActivity.getEmployees().stream()
                .anyMatch(e -> e.getInitials().equalsIgnoreCase(initials));
        assertTrue(assigned);
    }

    @When("a user deletes activity {string} from project {string}")
    public void a_user_deletes_activity_from_project(String activityName, String projectName) {
        Project p = model.getAllProjects().stream()
                .filter(proj -> proj.getProjectName().equals(projectName))
                .findFirst().orElse(null);
        assertNotNull(p);

        Activity toRemove = p.getActivityList().stream()
                .filter(a -> a.getActivityName().equalsIgnoreCase(activityName))
                .findFirst().orElse(null);
        assertNotNull(toRemove);

        p.getActivityList().remove(toRemove);
    }
}

