package at.technikum_wien.tourPlanner.view;


import at.technikum_wien.tourPlanner.viewModel.ImportWindowViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.util.List;


public class ImportWindowController {

    private final ImportWindowViewModel importWindowViewModel;
    @FXML
    public ListView jsonFileList;
    @FXML
    public Button importButton;
    @FXML
    public Button cancelButton;


    public ImportWindowController(ImportWindowViewModel importWindowViewModel) {
        this.importWindowViewModel = importWindowViewModel;
    }

    @FXML
    public void initialize() {
        importButton.disableProperty().bind(jsonFileList.getSelectionModel().selectedItemProperty().isNull());

        // set list data
        ObservableList<File> files = FXCollections.observableList(getJsonFileNamesFromDirectory());
        jsonFileList.setItems(files);

        // rename list cells to only show the name of the tour
        jsonFileList.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> fileListView) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(File file, boolean empty) {
                        super.updateItem(file, empty);
                        if (empty || file == null) {
                            setText(null);
                        } else {
                            setText(file.getName());
                        }
                    }
                };
            }
        });

    }

    public void importData() {
        importWindowViewModel.importData((File) jsonFileList.getSelectionModel().getSelectedItem());
        closeWindow();
    }

    public void closeWindow() {
        // close window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private List<File> getJsonFileNamesFromDirectory() {
        return importWindowViewModel.getJsonFileNamesFromDirectory();
    }
}
