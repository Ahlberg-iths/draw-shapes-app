package se.iths.ahlberg.drawshapesapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Locale;

public class Circle extends Shape {

    public Circle(double size, Color color, CanvasCoordinates coordinates) {
        super(size, color, coordinates);
    }

    @Override
    void draw(GraphicsContext context) {

        var x = this.coordinates.x();
        var y = this.coordinates.y();

        context.setFill(getColor());
        context.fillOval(x - getSize() / 2,y - getSize() / 2, getSize(), getSize());
    }

    @Override
    String toSVG() {

        var x = String.format(Locale.US,"%.2f", this.coordinates.x());
        var y = String.format(Locale.US,"%.2f", this.coordinates.y());

        return "<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + getSize() / 2 + "\" fill=\"#" + String.copyValueOf(getColor().toString().toCharArray(),2, 6) + "\" />";
    }

    @Override
    boolean isCoveringCoordinates(CanvasCoordinates point) {
        return Math.sqrt(Math.pow(Math.abs(this.coordinates.x() - point.x()), 2) +
                Math.pow(Math.abs(this.coordinates.y() - point.y()), 2)) <= getSize() / 2;
    }
}
