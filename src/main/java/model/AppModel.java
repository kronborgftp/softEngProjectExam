/**
 *
 *
 * @author Frederik, Lasse and Kim
 * Frederik created the getters and setters
 */
package model;

import java.time.LocalDate;
import java.util.*;

public class AppModel {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private final List<TimeEntry> timeEntries = new ArrayList<>();
    private final Map<String, Activity> standardActivities = new HashMap<>();
    private final Map<String, FixedActivity> fixedActivities = new HashMap<>();
    private static Employee loggedIn = null;
    private static Project currentProject = null;
    private static Activity currentActivity = null;


    public AppModel() {
        initializeFixedActivities();
    }

    // EMPLOYEES
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public static Employee getLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(Employee e) {
        loggedIn = e;
    }

    //Written by Lasse
    public Employee getEmployeeByInitials(String initials) {
        return employees.stream()
                .filter(e -> e.getInitials().equalsIgnoreCase(initials))
                .findFirst()
                .orElse(null);
    }

    //Written by Frederik
    public List<Employee> getAllEmployees() {
        return employees;
    }

    //Written by Kim
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

    public static void setCurrentProject(Project p) {
        currentProject = p;
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    //Written by Frederik
    public Project getProjectById(String id) {
        return projects.stream()
                .filter(p -> p.getProjectID().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    //Written by Kim
    public int getProjectCountByYear(String year) {
        long count = projects.stream()
                // .filter(p -> p.getStartDate().getYear() == year) // hvis man vil bruge start date
                .filter(p -> p.getProjectID().startsWith(year))
                .count();
        
        return (int) count;
    }

    //Written by Lasse
    public List<Project> getAllProjects() {
        return projects;
    }

    //Written by Frederik
    public void updateProjectName(Project project, String name) {
        project.setProjectName(name);
    }

    //Written by Lasse
    public void updateProjectDates(Project project, LocalDate startDate, LocalDate endDate) {
        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    //Written by Frederik
    public void changeProjectLeader(Project project, Employee leader) {
        project.assignProjectLeader(leader);
    }

    //Written by Frederik
    public void addActivityToProject(Project project, Activity activity) {
        activity.setParentProject(project); // Back reference
        project.addActivity(activity);
    }

    // ACTIVITIES
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    //Written by Frederik
    public Activity getActivityInProject(Project project, String activityId) {
        return project.getActivityList().stream()
                .filter(a -> a.getActivityId().equalsIgnoreCase(activityId))
                .findFirst()
                .orElse(null);
    }

    //Written by Kim
    public static void setCurrentActivity(Activity a) {
        currentActivity = a;
    }

    //Written by Kim
    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    //Written by Lasse
    public Activity getActivityGlobally(String activityId) {
        // First check standard activities
        if (standardActivities.containsKey(activityId)) {
            return standardActivities.get(activityId);
        }
        // Then check project activities
        for (Project p : projects) {
            for (Activity a : p.getActivityList()) {
                if (a.getActivityId().equalsIgnoreCase(activityId)) {
                    return a;
                }
            }
        }
        return null;
    }


    //Written by Frederik
    public void updateActivityName(Activity activity, String name) {
        activity.setActivityName(name);
    }

    //Written by Lasse
    public void updateActivityWeeks(Activity activity, int startWeek, int endWeek) {
        activity.setStartWeek(startWeek);
        activity.setEndWeek(endWeek);
    }

    //Written by Lasse
    public void updateBudgetedHours(Activity activity, int hours) {
        activity.setBudgetedHours(hours);
    }

    //Written by Frederik
    public void assignEmployeeToActivity(Employee employee, Activity activity) {
        activity.assignEmployee(employee);
        employee.assignActivity(activity);
    }

    //Written by Frederik
    public Activity getOrCreateFixedActivity(String id, String name) {
        if (!standardActivities.containsKey(id)) {
            Activity activity = new Activity(id, name, 0, 0, 0);
            standardActivities.put(id, activity);
        }
        return standardActivities.get(id);
    }

    //Written by Frederik
    public void initializeFixedActivities() {
        if (!fixedActivities.isEmpty()) return;
        //Fixed activities must be independent of projects,

        fixedActivities.put("VAC", new FixedActivity("VAC", "Vacation"));
        fixedActivities.put("SICK", new FixedActivity("SICK", "Sick Leave"));
        fixedActivities.put("COURSE", new FixedActivity("COURSE", "Course"));
    }


    // TIME ENTRIES

    //Written by Frederik
    public String logTimeEntry(Employee employee, Activity activity, double hours, String date) {
        String entryId = UUID.randomUUID().toString();
        TimeEntry entry = new TimeEntry(entryId, employee, activity, hours, date);
        timeEntries.add(entry);
        return entryId;
    }

    //Written by Frederik
    public TimeEntry getTimeEntryById(String id) {
        return timeEntries.stream()
                .filter(e -> e.getEntryID().equals(id))
                .findFirst()
                .orElse(null);
    }

    //Written by Frederik
    public List<TimeEntry> getAllTimeEntries() {
        return timeEntries;
    }

    //Written by Frederik
    public void updateTimeEntry(String entryId, double hours, String date) {
        TimeEntry entry = getTimeEntryById(entryId);
        if (entry != null) {
            entry.editEntry(hours, date);
        }
    }

    //Written by Frederik
    public void addTimeEntry(TimeEntry entry) {
        timeEntries.add(entry);
    }

}
