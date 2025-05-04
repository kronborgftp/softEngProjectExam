module hellofx {
    requires transitive javafx.controls;
    requires javafx.fxml;
 
    opens dtu.example.ui to javafx.fxml; // Gives access to fxml files
    opens controller to javafx.fxml; // jeg har bare gentaget for vores 3 src\main\java-mapper
    opens model to javafx.fxml;
    opens view to javafx.fxml;
    exports dtu.example.ui; // Exports the class inheriting from javafx.application.Application
    exports controller;
    exports model;
    exports view; 
}