/**
 *
 *
 * @author Frederik
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String activityId;
    private String activityName;
    private int budgetedHours;
    private int startWeek;
    private int endWeek;
    private List<Employee> employees;
    private Project parentProject;

    public Activity(String activityId, String activityName, int budgetedHours, int startWeek, int endWeek) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.budgetedHours = budgetedHours;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.employees = new ArrayList<>();
        this.parentProject = null;
    }

    // logic
    public void assignEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }

    // Getters

    public Project getParentProject() {
        return parentProject;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getBudgetedHours() {
        return budgetedHours;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    // Setters
    public void setParentProject(Project parentProject) {
        this.parentProject = parentProject;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setBudgetedHours(int budgetedHours) {
        this.budgetedHours = budgetedHours;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

}
