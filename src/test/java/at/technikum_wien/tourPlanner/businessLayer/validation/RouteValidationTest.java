package at.technikum_wien.tourPlanner.businessLayer.validation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class RouteValidationTest {

    static RouteValidation routeValidation;

    @BeforeEach
    void setUp() {
        routeValidation = new RouteValidation();
    }

    @Test
    void invalidLocationsCheckWithInvalidLocationCode() {
        assertTrue(routeValidation.invalidLocations("402", new String[]{"message"}));
    }

    @Test
    void invalidLocationsCheckWithValidLocationCode() {
        assertFalse(routeValidation.invalidLocations("0", new String[]{"ok"}));
    }

    @Test
    void invalidDistanceCheckWithInvalidDistance() {
        assertTrue(routeValidation.invalidDistance(0.0));
    }

    @Test
    void invalidDistanceCheckWithValidDistance() {
        assertFalse(routeValidation.invalidDistance(24.05));
    }

    @Test
    void invalidPedestrianDistanceCheckWithInvalidDistanceCode() {
        assertTrue(routeValidation.invalidPedestrianDistance("607"));
        assertTrue(routeValidation.invalidPedestrianDistance("608"));
    }

    @Test
    void invalidPedestrianDistanceCheckWithValidDistanceCode() {
        assertFalse(routeValidation.invalidPedestrianDistance("0"));
    }

    @Test
    void isUnknownErrorCheckWithUnknownErrorCode() {
        assertTrue(routeValidation.isUnknownError("500"));
    }

    @Test
    void isUnknownErrorCheckWithNormalCode() {
        assertFalse(routeValidation.isUnknownError("0"));
    }
}