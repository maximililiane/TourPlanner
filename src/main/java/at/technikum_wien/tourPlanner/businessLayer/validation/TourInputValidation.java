package at.technikum_wien.tourPlanner.businessLayer.validation;

public class TourInputValidation extends InputValidation {

    private final int NAME_LENGTH = 16;
    private final int LOCATION_LENGTH = 256;

    public TourInputValidation() {
        super();
    }

    public boolean validNameLength(String name) {
        return name.length() <= NAME_LENGTH;
    }

    public boolean validLocationLength(String location) {
        return location.length() <= LOCATION_LENGTH;
    }

    public boolean sameLocation(String from, String to) {
        return from.trim().toLowerCase().equals(to.trim().toLowerCase());
    }

}
