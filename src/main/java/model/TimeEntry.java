package model;

public class TimeEntry {
    private String entryID;
    private Employee employee;
    private Activity activity;
    private double hours;
    private String date;

    public TimeEntry(String entryID, Employee employee, Activity activity, double hours, String date) { //for normal work
        this.entryID = entryID;
        this.employee = employee;
        this.activity = activity;
        this.hours = hours;
        this.date = date;
    }

    public TimeEntry(String entryID, Employee employee, Activity activity, String date) { //For absence
        this.entryID = entryID;
        this.employee = employee;
        this.activity = activity;
        this.date = date;
        this.hours = -1; // special marker for absence
    }

    //  logic
    public void editEntry(double hours, String date) {
        this.hours = hours;
        this.date = date;
    }

    // Getters
    public String getEntryID() {
        return entryID;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Activity getActivity() {
        return activity;
    }

    public double getHours() {
        return hours;
    }

    public String getDate() {
        return date;
    }

    // Setters

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
