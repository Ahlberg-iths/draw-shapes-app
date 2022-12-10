package se.iths.ahlberg.drawshapesapp;

import javafx.scene.paint.Color;

public abstract class Shape {

    Color color;
    double size;

    abstract void draw(/*TODO::x,y coordinate params?*/);

}
