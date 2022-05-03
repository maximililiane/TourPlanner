package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.viewModel.MainWindowViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainWindowController {

    @FXML
    private SearchBarController searchBarController;    // injected controller of SearchBar.fxml

    private final MainWindowViewModel mainViewModel;

    public MainWindowController(MainWindowViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public MainWindowViewModel getMainViewModel() {
        return mainViewModel;
    }

    @FXML
    void initialize() {
    }

    //TODO:
}