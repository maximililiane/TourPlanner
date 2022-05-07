package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.dataAccessLayer.database.DatabaseConnector;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.LogRepository;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.viewModel.DescriptionViewModel;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;
import at.technikum_wien.tourPlanner.viewModel.MainWindowViewModel;
import at.technikum_wien.tourPlanner.viewModel.SearchBarViewModel;

import java.sql.SQLException;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final DescriptionViewModel descriptionViewModel;
    private final ListViewModel listViewModel;
    private TourRepository tourRepository;
    private LogRepository logRepository;
    // private final Injector injector;


    public ControllerFactory() {
        setUpDatabase();
        TourService tourService = new TourService(tourRepository);
        // this.injector = new Injector();
        this.searchBarViewModel = new SearchBarViewModel();
        this.mainWindowViewModel = new MainWindowViewModel(searchBarViewModel);
        this.descriptionViewModel = new DescriptionViewModel(tourService);
        this.listViewModel = new ListViewModel(tourService);
    }

    private void setUpDatabase() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            databaseConnector.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.tourRepository = new TourRepository(databaseConnector.getConnection());
        //TODO: this.logRepository = new LogRepository(databaseConnector.getConnection());
    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
        if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        } else if (controllerClass == DescriptionWindowController.class) {
            return new DescriptionWindowController(descriptionViewModel);
        } else if (controllerClass == ListViewController.class) {
            return new ListViewController(listViewModel);
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }


    //
    // Singleton-Pattern with early-binding
    //
    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }
}
