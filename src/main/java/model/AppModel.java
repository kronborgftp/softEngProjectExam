package model;

import java.util.*;

import java.time.LocalDate;

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

    public static void setCurrentProject(Project p) {
        currentProject = p;
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    public Project getProjectById(String id) {
        return projects.stream()
                .filter(p -> p.getProjectID().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public int getProjectCountByYear(String year) {
        long count = projects.stream()
                // .filter(p -> p.getStartDate().getYear() == year) // hvis man vil bruge start date
                .filter(p -> p.getProjectID().startsWith(year))
                .count();
        
        return (int) count;
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public void updateProjectName(Project project, String name) {
        project.setProjectName(name);
    }

    public void updateProjectDates(Project project, LocalDate startDate, LocalDate endDate) {
        project.setStartDate(startDate);
        project.setEndDate(endDate);
    }

    public void changeProjectLeader(Project project, Employee leader) {
        project.assignProjectLeader(leader);
    }

    public void addActivityToProject(Project project, Activity activity) {
        activity.setParentProject(project); // Back reference
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

    public static void setCurrentActivity(Activity a) {
        currentActivity = a;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

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

    public Activity getOrCreateFixedActivity(String id, String name) {
        if (!standardActivities.containsKey(id)) {
            Activity activity = new Activity(id, name, 0, 0, 0);
            standardActivities.put(id, activity);
        }
        return standardActivities.get(id);
    }

    public void initializeFixedActivities() {
        if (!fixedActivities.isEmpty()) return;

        fixedActivities.put("VAC", new FixedActivity("VAC", "Vacation"));
        fixedActivities.put("SICK", new FixedActivity("SICK", "Sick Leave"));
        fixedActivities.put("COURSE", new FixedActivity("COURSE", "Course"));
    }

    public FixedActivity getFixedActivity(String id) {
        return fixedActivities.get(id.toUpperCase());
    }

    public Collection<FixedActivity> getAllFixedActivities() {
        return fixedActivities.values();
    }


    // TIME ENTRIES

    public String logTimeEntry(Employee employee, Activity activity, double hours, String date) {
        String entryId = UUID.randomUUID().toString();
        TimeEntry entry = new TimeEntry(entryId, employee, activity, hours, date);
        timeEntries.add(entry);
        return entryId;
    }

    public TimeEntry getTimeEntryById(String id) {
        return timeEntries.stream()
                .filter(e -> e.getEntryID().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<TimeEntry> getAllTimeEntries() {
        return timeEntries;
    }

    public void updateTimeEntry(String entryId, double hours, String date) {
        TimeEntry entry = getTimeEntryById(entryId);
        if (entry != null) {
            entry.editEntry(hours, date);
        }
    }

   /* public void deleteTimeEntry(String entryId) {
        timeEntries.removeIf(entry -> entry.getEntryID().equals(entryId));
    }*/

    public void addTimeEntry(TimeEntry entry) {
        timeEntries.add(entry);
    }

}
