package se.iths.ahlberg.drawshapesapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    double size;
    Color color;
    CanvasCoordinates coordinates;

    public Shape(double size, Color color, CanvasCoordinates coordinates) {
        this.size = size;
        this.color = color;
        this.coordinates = coordinates;
    }

    public static Shape of(ShapeChoice shapeChoice, double size, Color color, CanvasCoordinates coordinates) {
        return switch (shapeChoice) {
            case CIRCLE -> new Circle(size, color, coordinates);
            case SQUARE -> new Square(size, color, coordinates);
        };
    }

    abstract void draw(GraphicsContext context);

}
