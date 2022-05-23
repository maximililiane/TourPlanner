package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.businessLayer.validation.TourInputValidation;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TransportMode;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.concurrent.TransferQueue;

public class EditTourWindowViewModel {
    private final TourService tourService;
    private final TourInputValidation tourInputValidation = new TourInputValidation();

    private final StringProperty nameString = new SimpleStringProperty("");
    private final StringProperty fromString = new SimpleStringProperty("");
    private final StringProperty toString = new SimpleStringProperty("");
    private final StringProperty descriptionString = new SimpleStringProperty("");
    // not allowed to edit tour unless all fields are filled out
    private final BooleanBinding isEditDisabledBinding = Bindings.createBooleanBinding(() -> nameString.get().isEmpty()
            || fromString.get().isEmpty() || toString.get().isEmpty() || descriptionString.get().isEmpty());

    public EditTourWindowViewModel(TourService tourService) {
        this.tourService = tourService;
        nameString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
        fromString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
        toString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
        descriptionString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
    }

    public void editTour(Tour tour) {
        tourService.editTour(tour);
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

    public BooleanBinding editDisabledBinding() {
        return isEditDisabledBinding;
    }

    public void setNameStringProperty(String tourName) {
        nameString.set(tourName);
    }

    public void setFromString(String fromString) {
        this.fromString.set(fromString);
    }

    public void setToString(String toString) {
        this.toString.set(toString);
    }

    public void setDescriptionString(String descriptionString) {
        this.descriptionString.set(descriptionString);
    }

    public boolean invalidName() {
        return !tourInputValidation.validNameLength(nameStringProperty().get()) ||
                tourInputValidation.isBlankString(nameStringProperty().get());
    }

    public boolean invalidStartingPoint() {
        return !tourInputValidation.validLocationLength(fromStringProperty().get()) ||
                tourInputValidation.isBlankString(fromStringProperty().get());
    }

    public boolean invalidEndPoint() {
        return !tourInputValidation.validLocationLength(toStringProperty().get()) ||
                tourInputValidation.isBlankString(toStringProperty().get());
    }

    public boolean sameLocation() {
        return tourInputValidation.sameLocation(fromStringProperty().get(), toStringProperty().get());
    }

    public boolean invalidDescription() {
        return tourInputValidation.isBlankString(descriptionStringProperty().get());
    }

}
