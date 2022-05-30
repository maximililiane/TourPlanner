package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.viewModel.AddLogWindowViewModel;
import at.technikum_wien.tourPlanner.viewModel.ConnectionErrorWindowViewModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.LinkedList;

public class ConnectionErrorWindowController {

    private final ConnectionErrorWindowViewModel connectionErrorWindowViewModel;


    public ConnectionErrorWindowController(ConnectionErrorWindowViewModel connectionErrorWindowViewModel) {
        this.connectionErrorWindowViewModel=connectionErrorWindowViewModel;
    }

    public void closeApplication(){
        System.exit(0);
    }

}
