package controller;

import model.*;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.time.DayOfWeek;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        AppController controller = new AppController();

        // TEST DATA
        AppModel model = controller.getModel();

        // Add employees
        Employee e1 = new Employee("JD", "John Doe", new ArrayList<>());
        Employee e2 = new Employee("huba", "Hubert Baumeister", new ArrayList<>());
        Employee e3 = new Employee("WW", "Walter White", new ArrayList<>());
        model.addEmployee(e1);
        model.addEmployee(e2);
        model.addEmployee(e3);

        // Monday of week 10 and 25 in 2025
        WeekFields wf = WeekFields.ISO;
        LocalDate startDate = LocalDate.of(2025, 1, 4)
                .with(wf.weekOfYear(), 10)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());
        LocalDate endDate   = LocalDate.of(2025, 1, 4)
                .with(wf.weekOfYear(), 25)
                .with(wf.dayOfWeek(), DayOfWeek.MONDAY.getValue());

        // project
        Project p1 = new Project("24001", "Development Project", startDate, endDate);
        p1.assignProjectLeader(e1);
        model.addProject(p1);

        //activities
        Activity a1 = new Activity("A1", "Coding", 80, 10, 15);
        Activity a2 = new Activity("A2", "White-box testing", 120, 16, 22);
        model.addActivityToProject(p1, a1);
        model.addActivityToProject(p1, a2);

        // Assign employees to activities
        model.assignEmployeeToActivity(e1, a1);
        model.assignEmployeeToActivity(e2, a1);
        model.assignEmployeeToActivity(e2, a2);
        model.assignEmployeeToActivity(e3, a2);

        // Log time entries
        model.logTimeEntry(e1, a1, 4.0, "04-03-2025");
        model.logTimeEntry(e2, a1, 3.5, "05-03-2025");
        model.logTimeEntry(e2, a2, 5.0, "06-03-2025");
        model.logTimeEntry(e3, a2, 6.0, "10-03-2025");


        controller.run();
    }
}
