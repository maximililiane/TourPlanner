package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.businessLayer.TourLogService;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.dataAccessLayer.database.DatabaseConnector;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourLogRepository;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.viewModel.*;

import java.sql.SQLException;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final DescriptionViewModel descriptionViewModel;
    private final ListViewModel listViewModel;
    private final TourLogViewModel tourLogViewModel;
    private final AddTourWindowViewModel addTourWindowViewModel;
    private final EditTourWindowViewModel editTourWindowViewModel;
    private final ExportDataWindowViewModel exportDataWindowViewModel;
    private final ImportWindowViewModel importWindowViewModel;
    private TourRepository tourRepository;
    private TourLogRepository tourLogRepository;

    public ControllerFactory() {
        setUpDatabase();
        TourService tourService = new TourService(tourRepository);
        TourLogService tourLogService = new TourLogService(tourRepository, tourLogRepository);
        this.searchBarViewModel = new SearchBarViewModel();
        this.mainWindowViewModel = new MainWindowViewModel();
        this.descriptionViewModel = new DescriptionViewModel(tourService);
        this.listViewModel = new ListViewModel(tourService);
        this.tourLogViewModel = new TourLogViewModel(tourLogService);
        this.addTourWindowViewModel = new AddTourWindowViewModel(tourService);
        this.editTourWindowViewModel = new EditTourWindowViewModel(tourService);
        this.exportDataWindowViewModel = new ExportDataWindowViewModel(tourService, tourLogService);
        this.importWindowViewModel = new ImportWindowViewModel(tourService, tourLogService);
    }

    private void setUpDatabase() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try {
            databaseConnector.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.tourRepository = new TourRepository(databaseConnector.getConnection());
        this.tourLogRepository = new TourLogRepository(databaseConnector.getConnection());
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
        } else if (controllerClass == TourLogWindowController.class) {
            return new TourLogWindowController(tourLogViewModel);
        } else if (controllerClass == AddTourWindowController.class) {
            return new AddTourWindowController(addTourWindowViewModel);
        } else if (controllerClass == EditTourWindowController.class) {
            return new EditTourWindowController(editTourWindowViewModel);
        } else if (controllerClass == ExportDataWindowController.class) {
            return new ExportDataWindowController(exportDataWindowViewModel);
        } else if (controllerClass == ImportWindowController.class) {
            return new ImportWindowController(importWindowViewModel);
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
