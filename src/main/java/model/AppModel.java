package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AppModel {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private final List<TimeEntry> timeEntries = new ArrayList<>();
    private final Map<String, Activity> standardActivities = new HashMap<>();

    // EMPLOYEES

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

    // PROJECTS

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
        activity.setParentProject(project); // Back reference
        project.addActivity(activity);
    }

    // ACTIVITIES

    public Activity getActivityInProject(Project project, String activityId) {
        return project.getActivityList().stream()
                .filter(a -> a.getActivityId().equalsIgnoreCase(activityId))
                .findFirst()
                .orElse(null);
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

    public List<Activity> getAllActivities() {
        List<Activity> all = new ArrayList<>(standardActivities.values());
        for (Project project : projects) {
            all.addAll(project.getActivityList());
        }
        return all;
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

    public Activity getOrCreateStandardActivity(String id, String name) {
        if (!standardActivities.containsKey(id)) {
            Activity activity = new Activity(id, name, 0, 0, 0);
            standardActivities.put(id, activity);
        }
        return standardActivities.get(id);
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
