package at.technikum_wien.tourPlanner.viewModel;

import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.businessLayer.validation.TourInputValidation;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TransportMode;
import com.itextpdf.layout.element.Link;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.TransferQueue;

public class EditTourWindowViewModel {
    private final TourService tourService;
    private ObservableList<Tour> tours;
    private final TourInputValidation tourInputValidation = new TourInputValidation();
    private final LoggerWrapper logger= LoggerFactory.getLogger();

    private final StringProperty nameString = new SimpleStringProperty("");
    private final StringProperty fromString = new SimpleStringProperty("");
    private final StringProperty toString = new SimpleStringProperty("");
    private final StringProperty descriptionString = new SimpleStringProperty("");
    // not allowed to edit tour unless all fields are filled out
    private final BooleanBinding isEditDisabledBinding = Bindings.createBooleanBinding(() -> nameString.get().isEmpty()
            || fromString.get().isEmpty() || toString.get().isEmpty() || descriptionString.get().isEmpty());

    public EditTourWindowViewModel(TourService tourService) {
        this.tourService = tourService;
        this.tours = tourService.getTours();
        nameString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
        fromString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
        toString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
        descriptionString.addListener((arg, oldVal, newVal) -> isEditDisabledBinding.invalidate());
    }

    public void editTour(int tourId, TransportMode transportMode) {
        Tour oldTour = tours.stream().filter(t -> t.getUid() == tourId).findFirst().get();
        Tour newTour = new Tour(nameStringProperty().get(), fromStringProperty().get(), toStringProperty().get(),
                transportMode, descriptionStringProperty().get());
        newTour.setLogs(oldTour.getLogs());
        newTour.setPopularity();
        newTour.setUid(oldTour.getUid());
        logger.info("User tries edit a tour in the database; Tourname: " + newTour.getName());
        tourService.editTour(newTour);
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
