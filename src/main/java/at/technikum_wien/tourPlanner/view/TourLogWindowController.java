package at.technikum_wien.tourPlanner.view;
import at.technikum_wien.tourPlanner.LogViewUtils.LogViewRow;
import at.technikum_wien.tourPlanner.listViewUtils.ListViewRow;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.viewModel.TourLogViewModel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class TourLogWindowController {

    private ObservableList<TourLog> logs;

    @FXML
    public TableView logTable;
    @FXML
    public TableColumn uidColumn;
    @FXML
    public TableColumn tourIDColumn;
    @FXML
    public TableColumn dateColumn;
    @FXML
    public TableColumn commentColumn;
    @FXML
    public TableColumn difficultyColumn;
    @FXML
    public TableColumn totalTimeColumn;
    @FXML
    public TableColumn ratingColumn;

    private final TourLogViewModel tourLogViewModel;

    public TourLogWindowController(TourLogViewModel tourLogViewModel){
        this.tourLogViewModel=tourLogViewModel;
        this.logs= tourLogViewModel.getList();
        this.logs.addListener(new ListChangeListener<TourLog>() {
            @Override
            public void onChanged(Change<? extends TourLog> change) {
                setListToTable(logs);
            }
        });
    }
    @FXML
    public void initialize() {
        initializeTable();
        setListToTable(tourLogViewModel.getList());
    }

    public void initializeTable() {
        associateColumns();
        /*logTable.setRowFactory(tv -> {
            TableRow<ListViewRow> row = new TableRow<>();
            CustomContextMenu cm = new CustomContextMenu();
            cm.getDetailsMenuItem().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    detailsButtonPressed(row.getItem().getUid());
                }
            });
            cm.getLogsMenuItem().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    logsButtonPressed(row.getItem().getUid());
                }
            });
            cm.getEditMenuItem().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    editTourButtonPressed(row.getItem().getUid());
                }
            });
            row.setContextMenu(cm.getCm());
            return row;
        });*/
    }

    public void associateColumns() {

        uidColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("uid"));
        tourIDColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("tourUID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("date"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("comment"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("difficulty"));
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("totalTime"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<ListViewRow, String>("rating"));

    }

    private void addListToTable(List<TourLog> list) {
        for (TourLog l : list) {
            addLogToTable(l);
        }
    }

    private void addLogToTable(TourLog l) {
        LogViewRow dataRow = new LogViewRow(l);
        logTable.getItems().add(dataRow);
    }

    private void setListToTable(ObservableList<TourLog> list) {
        logTable.getItems().removeAll(logTable.getItems());
        for (TourLog t : list) {
            addLogToTable(t);
            System.out.println(t.toString());
        }

    }
}
