package dtu.example.ui;

import model.Employee;
import model.Project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static String loggedIn = null; // for at vise hvilken employee der på nuværende tid er logget in
    private List<Employee> employeeList = new ArrayList<>();
    private List<String> projectList = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


    // employee handling
    public void logIn(String initials) {
        loggedIn = initials;
    }

    public void logOut() {
        loggedIn = null;
    }

    public void registerEmployee(String initials) {
        Employee newEmployee = new Employee(initials);
        employeeList.add(newEmployee);
    }

    public boolean hasEmployee(String name) { // man kan bruge både initials og name
        return employeeList.stream()
            .anyMatch(Employee -> 
                Employee.getInitials().equals(name) ||
                Employee.getName().equals(name)
            );
    }   

}