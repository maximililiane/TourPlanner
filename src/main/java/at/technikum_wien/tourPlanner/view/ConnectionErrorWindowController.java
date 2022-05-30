package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.viewModel.ConnectionErrorWindowViewModel;

public class ConnectionErrorWindowController {

    private final ConnectionErrorWindowViewModel connectionErrorWindowViewModel;


    public ConnectionErrorWindowController(ConnectionErrorWindowViewModel connectionErrorWindowViewModel) {
        this.connectionErrorWindowViewModel = connectionErrorWindowViewModel;
    }

    public void closeApplication() {
        System.exit(0);
    }

}
