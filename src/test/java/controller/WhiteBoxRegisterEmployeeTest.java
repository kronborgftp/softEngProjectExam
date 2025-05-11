package controller;

import model.Activity;
import model.AppModel;
import model.Employee;
import model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.AppView;
import view.EmployeeView;
import view.ReportView;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WhiteBoxRegisterEmployeeTest {
    private EmployeeController controller;
    private AppModel model;
    private AppView appView;
    private EmployeeView employeeView;
    private Scanner scanner;

    public WhiteBoxRegisterEmployeeTest() {
        model = new AppModel();
        appView = mock(AppView.class);
        employeeView = mock(EmployeeView.class);
        controller = new EmployeeController(scanner, model, appView, employeeView);

    }

    @Test
    public void testInputSetA() { // initials equals "0"
        String initials = "0";
        String name = "John Doe";

        controller.registerEmployee(initials,name);

        assertEquals(model.hasEmployee("0"),false);
    }
    
    @Test
    public void testInputSetB() { // duplicate initials
        String initials = "jdoe";
        String name = "John Doe";
        model.addEmployee(new Employee(initials, name, new ArrayList<>()));

        controller.registerEmployee(initials,name);

        assertFalse(model.getAllEmployees().size() == 2);
    }

    @Test
    public void testInputSetC() { // initials are empty string
        String initials = "";
        String name = "John Doe";

        controller.registerEmployee(initials,name);

        assertEquals(model.hasEmployee(""),false);
    }

    @Test
    public void testInputSetD() { // initials are non-letters
        String initials = "1234";
        String name = "John Doe";

        controller.registerEmployee(initials,name);

        assertEquals(model.hasEmployee("1234"),false);
    }

    @Test
    public void testInputSetE() { // initials are letters
        String initials = "jndoe";
        String name = "John Doe";

        controller.registerEmployee(initials,name);

        assertEquals(model.hasEmployee("jndo"),true);
    }

    @Test
    public void testInputSetF() { // initials are 4 or less letters
        String initials = "jdoe";
        String name = "John Doe";

        controller.registerEmployee(initials,name);

        assertEquals(model.hasEmployee("jdoe"),true);
    }
}


