package controller;

import model.Activity;
import model.AppModel;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AppView;
import view.ReportView;

import java.util.ArrayList;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class ReportControllerTest {
    private ReportController controller;
    private AppModel model;
    private AppView appView;
    private ReportView reportView;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        model = new AppModel();
        appView = mock(AppView.class);
        reportView = mock(ReportView.class);
        scanner = mock(Scanner.class);
        controller = new ReportController(scanner, model, appView, reportView);
    }

    @Test
    void testProjectTimeReport_projectExists() {
        // Set up
        when(scanner.nextLine()).thenReturn("25001");
        Employee e = new Employee("JD", "John Doe", new ArrayList<>());
        Project p = new Project("25001", "Gooning Project", 10, 25, new ArrayList<>(), e);
        Activity a1 = new Activity("A1", "Gooning", 80, 10, 15);
        model.addEmployee(e);
        model.addProject(p);
        model.addActivityToProject(p, a1);
        model.logTimeEntry(e, a1, 4.0, "2025-03-04");

        // Execute
        controller.projectTimeReport();

        // Verify
        verify(reportView).printProjectTimeReport(eq(p), anyMap(), anyMap());
    }

    @Test
    void testProjectTimeReport_projectNotFound() {
        when(scanner.nextLine()).thenReturn("99999");

        controller.projectTimeReport();

        verify(reportView).printError("Project not found.");
    }
}

