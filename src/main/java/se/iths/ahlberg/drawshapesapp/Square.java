package se.iths.ahlberg.drawshapesapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Locale;

public class Square extends Shape {

    public Square(double size, Color color, CanvasCoordinates coordinates) {
        super(size, color, coordinates);
    }

    @Override
    void draw(GraphicsContext context) {

        context.setFill(getColor());
        context.fillRect(getCoordinates().x() - getSize() / 2,
                         getCoordinates().y() - getSize() / 2,
                            getSize(),
                            getSize()
        );
    }

    @Override
    String toSVG() {

        var x = String.format(Locale.US,"%.2f", getCoordinates().x() - getSize() / 2);
        var y = String.format(Locale.US,"%.2f", getCoordinates().y() - getSize() / 2);

        return "<rect x=\"" + x +
                "\" y=\"" + y +
                "\" width=\"" + getSize() +
                "\" height=\"" + getSize() +
                "\" fill=\"#" + getColor().toString().substring(2, 8) + "\" />";
    }

    @Override
    boolean isCoveringCoordinates(CanvasCoordinates point) {
         return (point.x() >= getCoordinates().x() - getSize() / 2 && point.x() <= (getCoordinates().x() + getSize() / 2)) &&
                (point.y() >= getCoordinates().y() - getSize() / 2 && point.y() <= (getCoordinates().y() + getSize() / 2));
    }
}
