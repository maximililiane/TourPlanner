package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.viewModel.DescriptionViewModel;
import at.technikum_wien.tourPlanner.viewModel.ListViewModel;
import at.technikum_wien.tourPlanner.viewModel.MainWindowViewModel;
import at.technikum_wien.tourPlanner.viewModel.SearchBarViewModel;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final DescriptionViewModel descriptionViewModel;
    private final ListViewModel listViewModel;


    public ControllerFactory() {
        searchBarViewModel = new SearchBarViewModel();
        mainWindowViewModel = new MainWindowViewModel(searchBarViewModel);
        descriptionViewModel = new DescriptionViewModel();
        listViewModel= new ListViewModel();
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
        } else if (controllerClass== ListViewController.class){
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
