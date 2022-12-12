package se.iths.ahlberg.drawshapesapp;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.List;

public class Model {

    private final ObjectProperty<Color> color;
    private final ObjectProperty<Number> size;
    private final BooleanProperty inSelectMode;
    private final ObjectProperty<ShapeChoice> shapeChoice;
    private final ObservableList<ShapeChoice> shapeChoiceList;
    private final ObservableList<Shape> currentShapesList;

    public Model () {
        this.color = new SimpleObjectProperty<>(Color.PALETURQUOISE);
        this.size = new SimpleObjectProperty<>(20.0);
        this.inSelectMode = new SimpleBooleanProperty(false);
        this.shapeChoice = new SimpleObjectProperty<>(ShapeChoice.CIRCLE);
        this.shapeChoiceList = FXCollections.observableList(List.of(ShapeChoice.SQUARE, ShapeChoice.CIRCLE));
        this.currentShapesList = FXCollections.observableArrayList();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public ObjectProperty<Number> sizeProperty() {
        return size;
    }

    public ObjectProperty<ShapeChoice> shapeChoiceProperty() {
        return shapeChoice;
    }

    public ObservableList<ShapeChoice> getShapeChoiceList() {
        return shapeChoiceList;
    }

    public BooleanProperty inSelectModeProperty() {
        return inSelectMode;
    }

    public Color getColor() {
        return color.get();
    }

    public Number getSize() {
        return size.get();
    }

    public ShapeChoice getShapeChoice() {
        return shapeChoice.get();
    }

    public boolean isInSelectMode() {
        return inSelectMode.get();
    }

    public void addToCurrentShapesList(Shape shape) {
        this.currentShapesList.add(shape);
    }

    public ObservableList<Shape> getCurrentShapesList() {
        return currentShapesList;
    }
}
