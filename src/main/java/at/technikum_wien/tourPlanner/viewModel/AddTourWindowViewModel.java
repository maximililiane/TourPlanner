package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddTourWindowViewModel {

    private final TourService tourService;

    private final StringProperty nameString = new SimpleStringProperty("");
    private final StringProperty fromString = new SimpleStringProperty("");
    private final StringProperty toString = new SimpleStringProperty("");
    private final StringProperty descriptionString = new SimpleStringProperty("");
    // not allowed to add tour unless all fields are filled out
    private final BooleanBinding isAddDisabledBinding = Bindings.createBooleanBinding(() -> nameString.get().isEmpty()
            || fromString.get().isEmpty() || toString.get().isEmpty() || descriptionString.get().isEmpty());

    public AddTourWindowViewModel(TourService tourService) {
        this.tourService = tourService;
        nameString.addListener((arg, oldVal, newVal) -> isAddDisabledBinding.invalidate());
        fromString.addListener((arg, oldVal, newVal) -> isAddDisabledBinding.invalidate());
        toString.addListener((arg, oldVal, newVal) -> isAddDisabledBinding.invalidate());
        descriptionString.addListener((arg, oldVal, newVal) -> isAddDisabledBinding.invalidate());
    }

    public StringProperty nameStringProperty() {
        return nameString;
    }

    public StringProperty fromStringProperty() {
        return fromString;
    }

    public StringProperty toStringProperty() {
        return toString;
    }

    public StringProperty descriptionStringProperty() {
        return descriptionString;
    }

    public BooleanBinding addDisabledBinding() {
        return isAddDisabledBinding;
    }

    public void addTour(Tour tour) {
        tourService.addTour(tour);
    }

}
