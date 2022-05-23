package at.technikum_wien.tourPlanner.businessLayer.validation;

public abstract class InputValidation {

    // blank string -> string that only consists of whitespaces
    public boolean isBlankString(String string) {
        return string == null || string.trim().length() == 0;
    }

}
