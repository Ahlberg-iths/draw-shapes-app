package se.iths.ahlberg.drawshapesapp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
}
