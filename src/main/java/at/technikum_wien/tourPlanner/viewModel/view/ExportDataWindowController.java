package at.technikum_wien.tourPlanner.viewModel.view;

import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.viewModel.ExportDataWindowViewModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ExportDataWindowController {

    private final ExportDataWindowViewModel exportDataWindowViewModel;
    public ListView tourList;
    public Button exportButton;
    public Button cancelButton;
    private ObservableList<Tour> tourObservableList;

    public ExportDataWindowController(ExportDataWindowViewModel exportDataWindowViewModel) {
        this.exportDataWindowViewModel = exportDataWindowViewModel;
    }

    @FXML
    public void initialize() {
        this.tourObservableList = getTours();

        exportButton.disableProperty().bind(tourList.getSelectionModel().selectedItemProperty().isNull());

        // set list data
        tourList.setItems(tourObservableList);

        // rename list cells to only show the name of the tour
        tourList.setCellFactory(new Callback<ListView<Tour>, ListCell<Tour>>() {
            @Override
            public ListCell<Tour> call(ListView<Tour> tourListView) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Tour tour, boolean empty) {
                        super.updateItem(tour, empty);
                        if (empty || tour == null) {
                            setText(null);
                        } else {
                            setText(tour.getName());
                        }
                    }
                };
            }
        });
    }

    public void exportData(ActionEvent actionEvent) {
        exportDataWindowViewModel.exportData((Tour) tourList.getSelectionModel().getSelectedItem());
        closeWindow();
    }

    public void closeWindow() {
        // close window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private ObservableList<Tour> getTours() {
        return exportDataWindowViewModel.getTours();
    }
}
