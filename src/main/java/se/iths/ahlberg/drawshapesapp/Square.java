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

        var x = this.coordinates.x();
        var y = this.coordinates.y();

        context.setFill(getColor());
        context.fillRect(x - getSize() / 2,y - getSize() / 2, getSize(), getSize());
    }

    @Override
    String toSVG() {

        var x = String.format(Locale.US,"%.2f", this.coordinates.x() - getSize() / 2);
        var y = String.format(Locale.US,"%.2f", this.coordinates.y() - getSize() / 2);

        return "<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + getSize() + "\" height=\"" + getSize() + "\" fill=\"#" + String.copyValueOf(getColor().toString().toCharArray(),2, 6) + "\" />";
    }

    @Override
    boolean isCoveringCoordinates(CanvasCoordinates point) {
        //TODO::
         return (point.x() >= this.coordinates.x() - getSize() / 2 && point.x() <= (this.coordinates.x() + getSize() / 2)) &&
                (point.y() >= this.coordinates.y() - getSize() / 2 && point.y() <= (this.coordinates.y() + getSize() / 2));
    }
}
