package se.iths.ahlberg.drawshapesapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends Shape {

    public Square(double size, Color color, CanvasCoordinates coordinates) {
        super(size, color, coordinates);
    }

    @Override
    void draw(GraphicsContext context) {

        var x = this.coordinates.x();
        var y = this.coordinates.y();

        context.setFill(this.color);
        context.fillRect(x - this.size / 2,y - this.size / 2, this.size, this.size);
    }

    @Override
    String toSVG() {

        var x = String.format("%.2f", this.coordinates.x() - this.size / 2);
        var y = String.format("%.2f", this.coordinates.y() - this.size / 2);

        return "<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + this.size + "\" height=\"" + this.size + "\" fill=\"#" + String.copyValueOf(this.color.toString().toCharArray(),2, 6) + "\" />";
    }
}
