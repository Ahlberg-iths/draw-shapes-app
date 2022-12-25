package se.iths.ahlberg.drawshapesapp;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public abstract class Shape {

    private final ObjectProperty<Double> size = new SimpleObjectProperty<>();
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private final CanvasCoordinates coordinates;

    public Shape(double size, Color color, CanvasCoordinates coordinates) {
        this.size.set(size);
        this.color.set(color);
        this.coordinates = coordinates;
    }

    public static Shape of(ShapeChoice shapeChoice, double size, Color color, CanvasCoordinates coordinates) {
        return switch (shapeChoice) {
            case CIRCLE -> new Circle(size, color, coordinates);
            case SQUARE -> new Square(size, color, coordinates);
        };
    }

    public static Shape fromServerString(String serverMessage) {
        Pattern.compile("data-dsapp=\"(.+)\"").matcher(serverMessage).results().forEach(res -> System.out.println(res.group(1)));
        var data = Pattern.compile("data-dsapp=\"(.+)\"").matcher(serverMessage).results().reduce((a, b) -> b).get().group(1).split(",");

        ShapeChoice shapeChoice = data[0].equals("C") ? ShapeChoice.CIRCLE : ShapeChoice.SQUARE;
        double size = Double.parseDouble(data[1]);
        Color color = Color.web(data[2]);
        CanvasCoordinates coordinates = new CanvasCoordinates(Double.parseDouble(data[3]), Double.parseDouble(data[4]));
        return of(shapeChoice, size, color, coordinates);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public Double getSize() {
        return size.get();
    }

    public ObjectProperty<Double> sizeProperty() {
        return size;
    }

    abstract void draw(GraphicsContext context);

    abstract String toSVG();

    abstract boolean isCoveringCoordinates(CanvasCoordinates point);

    public CanvasCoordinates getCoordinates() {
        return coordinates;
    }
}
