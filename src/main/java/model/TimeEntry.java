package model;

public class TimeEntry {

    String entryID;
    Employee employee;
    Activity activity;
    double hours;
    String date;

    public TimeEntry(String entryID, Employee employee, Activity activity, double hours, String date) {
        this.entryID = entryID;
        this.employee = employee;
        this.activity = activity;
        this.hours = hours;
        this.date = date;
    }

    public void editEntry(String entryID, Employee employee, Activity activity, double hours, String date) {

    }
}
