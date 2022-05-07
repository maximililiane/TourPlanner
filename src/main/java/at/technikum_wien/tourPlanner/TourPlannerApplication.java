package at.technikum_wien.tourPlanner;

import at.technikum_wien.tourPlanner.models.Tour;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class TourPlannerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLDependencyInjection.load("mainWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH

        Scene scene = new Scene(root);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

        //TODO: remove this test

//        List<Integer> tours = new ArrayList<Integer>();
//        ObservableList<Integer> observedTours = FXCollections.observableList(tours);
//
//        observedTours.addListener(new ListChangeListener<Integer>() {
//            @Override
//            public void onChanged(Change<? extends Integer> change) {
//                System.out.println("Hey, a change occurred...");
//            }
//        });
//
//        tours.add(1);
//        System.out.println(observedTours);
//
//        tours.add(2);
//        System.out.println(observedTours);
//
//        tours.add(3);
//        System.out.println(observedTours);
//        observedTours.add(4);
//        System.out.println(observedTours);
//
//        tours.remove(0);
//        System.out.println(observedTours);
    }
}