module hellofx {
    requires junit;
 
    //opens dtu.example.ui to javafx.fxml; // Gives access to fxml files
    opens controller to javafx.fxml; // jeg har bare gentaget "opens" og "exports" for vores 3 src\main\java-mapper
    opens model to javafx.fxml;
    opens view to javafx.fxml;
    exports controller;
    exports model;
    exports view; 
}