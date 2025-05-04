package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import view.EmployeeView;
import view.AppView;

public class AppModel {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private final List<TimeEntry> timeEntries = new ArrayList<>();
    

    // EMPLOYEES
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee getEmployeeByInitials(String initials) {
        return employees.stream()
                .filter(e -> e.getInitials().equalsIgnoreCase(initials))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public boolean hasEmployee(String name) { // man kan bruge både initials og name
        return employees.stream()
            .anyMatch(Employee -> 
                Employee.getInitials().equals(name) ||
                Employee.getName().equals(name)
            );
    }   

    // PROJECTS
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public void addProject(Project project) {
        projects.add(project);
    }

    public Project getProjectById(String id) {
        return projects.stream()
                .filter(p -> p.getProjectID().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public void updateProjectName(Project project, String name) {
        project.setProjectName(name);
    }

    public void updateProjectWeeks(Project project, int startWeek, int endWeek) {
        project.setStartWeek(startWeek);
        project.setEndWeek(endWeek);
    }

    public void changeProjectLeader(Project project, Employee leader) {
        project.assignProjectLeader(leader);
    }

    public void addActivityToProject(Project project, Activity activity) {
        activity.setParentProject(project); //back reference
        project.addActivity(activity);
    }

    // ACTIVITIES
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public Activity getActivityInProject(Project project, String activityId) {
        return project.getActivityList().stream()
                .filter(a -> a.getActivityId().equalsIgnoreCase(activityId))
                .findFirst()
                .orElse(null);
    }

    public Activity getActivityGlobally(String activityId) {
        for (Project p : projects) {
            for (Activity a : p.getActivityList()) {
                if (a.getActivityId().equalsIgnoreCase(activityId)) {
                    return a;
                }
            }
        }
        return null;
    }

    public void updateActivityName(Activity activity, String name) {
        activity.setActivityName(name);
    }

    public void updateActivityWeeks(Activity activity, int startWeek, int endWeek) {
        activity.setStartWeek(startWeek);
        activity.setEndWeek(endWeek);
    }

    public void updateBudgetedHours(Activity activity, int hours) {
        activity.setBudgetedHours(hours);
    }

    public void assignEmployeeToActivity(Employee employee, Activity activity) {
        activity.assignEmployee(employee);
        employee.assignActivity(activity);
    }

    // TIME ENTRY
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public void logTimeEntry(Employee employee, Activity activity, double hours, String date) {
        TimeEntry entry = new TimeEntry(UUID.randomUUID().toString(), employee, activity, hours, date);
        timeEntries.add(entry);
    }

    public List<TimeEntry> getAllTimeEntries() {
        return timeEntries;
    }
}
