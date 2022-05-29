package at.technikum_wien.tourPlanner.businessLayer.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TourInputValidationTest {

    static TourInputValidation tourInputValidation;

    @BeforeEach
    void setUp() {
        tourInputValidation = new TourInputValidation();
    }

    @Test
    void isBlankStringWithBlankString() {
        String testString = "          ";
        assertTrue(tourInputValidation.isBlankString(testString));
    }

    @Test
    void isBlankStringWithEmptyString() {
        String testString = "";
        assertTrue(tourInputValidation.isBlankString(testString));
    }

    @Test
    void isBlankStringWithValidString() {
        String testString = "Test test";
        assertFalse(tourInputValidation.isBlankString(testString));
    }

    @Test
    void validNameLengthWithInvalidLength() {
        String testString = "This is an invalid name length since it is longer than 16 characters";
        assertFalse(tourInputValidation.validNameLength(testString));
    }

    @Test
    void validNameLengthWithValidLength() {
        String tourName = "Valid Tour";
        assertTrue(tourInputValidation.validNameLength(tourName));
    }

    @Test
    void validLocationLengthWithInValidLength() {
        String testString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertFalse(tourInputValidation.validLocationLength(testString));
    }

    @Test
    void validLocationLengthWithValidLength() {
        String validLocation = "Vienna";
        assertTrue(tourInputValidation.validLocationLength(validLocation));
    }

    @Test
    void sameLocationWithSameLocation() {
        String location1 = "VIENNA";
        String location2 = "Vienna";
        assertTrue(tourInputValidation.sameLocation(location1, location2));
    }

    @Test
    void sameLocationWithDifferentLocations() {
        String location1 = "Vienna";
        String location2 = "Eisenstadt";
        assertFalse(tourInputValidation.sameLocation(location1, location2));
    }
}