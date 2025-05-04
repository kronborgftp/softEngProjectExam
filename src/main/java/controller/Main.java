package controller;

import model.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AppController controller = new AppController();

        // TEST DATA
        AppModel model = controller.getModel();

        // Add employees
        Employee e1 = new Employee("JD", "John Doe", new ArrayList<>());
        Employee e2 = new Employee("huba", "Hubert Baumeister", new ArrayList<>());
        Employee e3 = new Employee("WW", "Walter WHite", new ArrayList<>());
        model.addEmployee(e1);
        model.addEmployee(e2);
        model.addEmployee(e3);

        // project
        Project p1 = new Project("24001", "Gooning Project", 10, 25, new ArrayList<>(), e1);
        model.addProject(p1);

        //activities
        Activity a1 = new Activity("A1", "Gooning", 80, 10, 15);
        Activity a2 = new Activity("A2", "Gooning Max!", 120, 16, 22);
        model.addActivityToProject(p1, a1);
        model.addActivityToProject(p1, a2);

        // Assign employees to activities
        model.assignEmployeeToActivity(e1, a1);
        model.assignEmployeeToActivity(e2, a1);
        model.assignEmployeeToActivity(e2, a2);
        model.assignEmployeeToActivity(e3, a2);

        // Log time entries
        model.logTimeEntry(e1, a1, 4.0, "11-03-2025");
        model.logTimeEntry(e2, a1, 3.5, "11-03-2025");
        model.logTimeEntry(e2, a2, 5.0, "12-03-2025");
        model.logTimeEntry(e3, a2, 6.0, "24-03-2025");


        controller.run();
    }
}
