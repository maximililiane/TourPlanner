package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.viewModel.SearchBarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchViewController {

    private final SearchBarViewModel searchBarViewModel;
    @FXML
    public ListView searchResultListView;
    @FXML
    public Label resultsLabel;

    public SearchViewController(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;
    }

    public SearchBarViewModel getSearchBarViewModel() {
        return searchBarViewModel;
    }

    @FXML
    void initialize() {

    }

}
