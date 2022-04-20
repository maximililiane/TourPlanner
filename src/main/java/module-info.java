module at.technikum_wien.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.sql;


    opens at.technikum_wien.tourPlanner to javafx.fxml;
    opens at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest to com.fasterxml.jackson.databind;
    exports at.technikum_wien.tourPlanner;
    exports at.technikum_wien.tourPlanner.view;
    exports at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;
    opens at.technikum_wien.tourPlanner.view to javafx.fxml;
    exports at.technikum_wien.tourPlanner.listViewUtils;
    opens at.technikum_wien.tourPlanner.listViewUtils to javafx.base;
}