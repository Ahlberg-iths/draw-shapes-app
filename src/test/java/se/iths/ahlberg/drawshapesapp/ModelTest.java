package se.iths.ahlberg.drawshapesapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    Model testModel = new Model();

    @Test
    @DisplayName("undo should not be available at app start-up")
    void undoShouldNotBeAvailableAtAppStartUp() {

        assertTrue(testModel.undoIsUnavailableProperty().getValue());
    }

    @Test
    @DisplayName("redo should not be available at app start-up")
    void redoShouldNotBeAvailableAtAppStartUp() {

        assertTrue(testModel.redoIsUnavailableProperty().getValue());
    }
}
