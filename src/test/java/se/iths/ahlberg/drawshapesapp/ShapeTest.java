package se.iths.ahlberg.drawshapesapp;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeTest {

    CanvasCoordinates testCoordinates = new CanvasCoordinates(100, 100);
    Color testColor = Color.DARKSALMON;
    double testSize = 50.0;

    @Test
    @DisplayName("calling the Shape.of method with ShapeChoice set to CIRCLE should return a Circle object")
    void callingTheShapeOfMethodWithShapeChoiceSetToCircleShouldReturnACircleObject() {

        var shape = Shape.of(ShapeChoice.CIRCLE, testSize, testColor, testCoordinates);

        assertEquals(Circle.class, shape.getClass());
    }

    @Test
    @DisplayName("calling the Shape.of method with ShapeChoice set to SQUARE should return a Square object")
    void callingTheShapeOfMethodWithShapeChoiceSetToSquareShouldReturnASquareObject() {

        var shape = Shape.of(ShapeChoice.SQUARE, testSize, testColor, testCoordinates);

        assertEquals(Square.class, shape.getClass());
    }
}
