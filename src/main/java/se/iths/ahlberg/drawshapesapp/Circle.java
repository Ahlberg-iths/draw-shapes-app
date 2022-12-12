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

        context.setFill(this.color);
        context.fillOval(x - this.size / 2,y - this.size / 2, this.size, this.size);
    }

    @Override
    String toSVG() {

        var x = String.format(Locale.US,"%.2f", this.coordinates.x());
        var y = String.format(Locale.US,"%.2f", this.coordinates.y());

        return "<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"" + this.size / 2 + "\" fill=\"#" + String.copyValueOf(this.color.toString().toCharArray(),2, 6) + "\" />";
    }
}
