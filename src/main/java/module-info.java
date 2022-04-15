module at.technikum_wien.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens at.technikum_wien.tourPlanner to javafx.fxml;
    exports at.technikum_wien.tourPlanner;
    exports at.technikum_wien.tourPlanner.view;
    opens at.technikum_wien.tourPlanner.view to javafx.fxml;
}