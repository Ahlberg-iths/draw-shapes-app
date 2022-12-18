package se.iths.ahlberg.drawshapesapp;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import java.util.List;

public class Model {

    private final ObjectProperty<Color> color;
    private final ObjectProperty<Number> size;
    private final ObjectProperty<ShapeChoice> shapeChoice;
    private final ObservableList<ShapeChoice> shapeChoiceList;
    private final ObservableList<Shape> currentShapesList;
    private final ObservableList<Command> undoList;
    private final ObservableList<Command> redoList;
    private final ObjectProperty<Boolean> isUndoUnavailable;
    private final ObjectProperty<Boolean> isRedoUnavailable;

    public Model () {
        this.color = new SimpleObjectProperty<>(Color.web("#663366"));
        this.size = new SimpleObjectProperty<>(200.0);
        this.shapeChoice = new SimpleObjectProperty<>(ShapeChoice.SQUARE);
        this.shapeChoiceList = FXCollections.observableList(List.of(ShapeChoice.values()));
        this.currentShapesList = FXCollections.observableArrayList(shape -> new Observable[] {shape.colorProperty(), shape.sizeProperty()});
        this.undoList = FXCollections.observableArrayList();
        this.redoList = FXCollections.observableArrayList();
        this.isUndoUnavailable = new SimpleObjectProperty<>(true);
        this.isRedoUnavailable = new SimpleObjectProperty<>(true);
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

    public Color getColor() {
        return color.get();
    }

    public Number getSize() {
        return size.get();
    }

    public ShapeChoice getShapeChoice() {
        return shapeChoice.get();
    }

    public ObservableList<Shape> getCurrentShapesList() {
        return currentShapesList;
    }

    public ObservableList<Command> getUndoList() {
        return undoList;
    }

    public ObservableList<Command> getRedoList() {
        return redoList;
    }

    public ObservableValue<Boolean> isUndoUnavailableProperty() {
        return isUndoUnavailable;
    }

    public void setIsUndoUnavailable(Boolean isUndoUnavailable) {
        this.isUndoUnavailable.set(isUndoUnavailable);
    }

    public ObjectProperty<Boolean> isRedoUnavailableProperty() {
        return isRedoUnavailable;
    }

    public void setIsRedoUnavailable(Boolean isRedoUnavailable) {
        this.isRedoUnavailable.set(isRedoUnavailable);
    }
}
