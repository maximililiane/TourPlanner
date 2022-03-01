module at.technikum_wien.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.technikum_wien.tourplanner to javafx.fxml;
    exports at.technikum_wien.tourplanner;
}