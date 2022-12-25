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

        context.setFill(getColor());
        context.fillOval(getCoordinates().x() - Calc.radiusFromDiameter(getSize()),
                         getCoordinates().y() - Calc.radiusFromDiameter(getSize()),
                            getSize(),
                            getSize()
        );
    }

    @Override
    String toSVG() {

        var x = String.format(Locale.US,"%.2f", getCoordinates().x());
        var y = String.format(Locale.US,"%.2f", getCoordinates().y());

        return "<circle cx=\"" + x +
                "\" cy=\"" + y +
                "\" r=\"" + Calc.radiusFromDiameter(getSize()) +
                "\" fill=\"#" + getColor().toString().substring(2, 8) +
                "\" data-dsapp=\"" +
                "C," +
                getSize() +
                ",#"+ getColor().toString().substring(2, 8) +
                "," + String.format(Locale.US, "%.2f", getCoordinates().x()) +
                "," + String.format(Locale.US, "%.2f", getCoordinates().y()) +
                "\" />";
    }

    @Override
    boolean isCoveringCoordinates(CanvasCoordinates point) {
        return Math.sqrt(Math.pow(Math.abs(getCoordinates().x() - point.x()), 2) +
                Math.pow(Math.abs(getCoordinates().y() - point.y()), 2)) <= Calc.radiusFromDiameter(getSize());
    }
}
