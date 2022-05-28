module at.technikum_wien.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires io;
    requires kernel;
    requires layout;
    requires org.apache.commons.io;

    opens at.technikum_wien.tourPlanner to javafx.fxml;
    opens at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest to com.fasterxml.jackson.databind;
    opens at.technikum_wien.tourPlanner.view to javafx.fxml;
    opens at.technikum_wien.tourPlanner.listViewUtils to javafx.base;
    opens at.technikum_wien.tourPlanner.LogViewUtils to javafx.base;
    opens at.technikum_wien.tourPlanner.models to com.fasterxml.jackson.databind;
    exports at.technikum_wien.tourPlanner.listViewUtils;
    exports at.technikum_wien.tourPlanner.LogViewUtils;
    exports at.technikum_wien.tourPlanner;
    exports at.technikum_wien.tourPlanner.view;
    exports at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;
    exports at.technikum_wien.tourPlanner.models to com.fasterxml.jackson.databind;
    exports at.technikum_wien.tourPlanner.models.serializers to com.fasterxml.jackson.databind;
    opens at.technikum_wien.tourPlanner.models.serializers to com.fasterxml.jackson.databind;
}