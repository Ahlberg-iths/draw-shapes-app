module se.iths.ahlberg.drawshapesapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.iths.ahlberg.drawshapesapp to javafx.fxml;
    exports se.iths.ahlberg.drawshapesapp;
}