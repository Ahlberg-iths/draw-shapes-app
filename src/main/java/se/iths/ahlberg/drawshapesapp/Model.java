package se.iths.ahlberg.drawshapesapp;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class Model {

    private final ObjectProperty<Color> color;
    private final ObjectProperty<Number> size;
    private final ObjectProperty<ShapeChoice> shapeChoice;
    private final ObservableList<ShapeChoice> shapeChoiceList;
    private final ObservableList<Shape> currentShapesList;
    private final ObservableList<Command> undoList;
    private final ObservableList<Command> redoList;
    private final ObjectProperty<Boolean> undoIsUnavailable;
    private final ObjectProperty<Boolean> redoIsUnavailable;

    PrintWriter printWriter;

    public Model () {
        this.color = new SimpleObjectProperty<>(Color.web("#663366"));
        this.size = new SimpleObjectProperty<>(200.0);
        this.shapeChoice = new SimpleObjectProperty<>(ShapeChoice.SQUARE);
        this.shapeChoiceList = FXCollections.observableList(List.of(ShapeChoice.values()));
        this.currentShapesList = FXCollections.observableArrayList(shape -> new Observable[] {shape.colorProperty(), shape.sizeProperty()});
        this.undoList = FXCollections.observableArrayList();
        this.redoList = FXCollections.observableArrayList();
        this.undoIsUnavailable = new SimpleObjectProperty<>(true);
        this.redoIsUnavailable = new SimpleObjectProperty<>(true);
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

    public ObservableValue<Boolean> undoIsUnavailableProperty() {
        return undoIsUnavailable;
    }

    public void setUndoIsUnavailable(Boolean undoIsUnavailable) {
        this.undoIsUnavailable.set(undoIsUnavailable);
    }

    public ObjectProperty<Boolean> redoIsUnavailableProperty() {
        return redoIsUnavailable;
    }

    public void setRedoIsUnavailable(Boolean redoIsUnavailable) {
        this.redoIsUnavailable.set(redoIsUnavailable);
    }

    String composeSVGElementWithCurrentShapes(double svgWidth, double svgHeight) {

        StringBuilder svg = new StringBuilder();
        svg.append("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" ")
                .append("width=\"").append(svgWidth).append("\" ")
                .append("height=\"").append(svgHeight).append("\">");

        getCurrentShapesList().forEach(shape -> svg.append("\n\t").append(shape.toSVG()));

        return svg.append("\n</svg>").toString();
    }

    void handleRedo() {
        Command command = getRedoList().remove(getRedoList().size() - 1);
        command.execute();
        getUndoList().add(command);
    }

    void handleUndo() {
        Command command = getUndoList().remove(getUndoList().size() - 1);
        command.undo();
        getRedoList().add(command);
    }

    void replaceShape(Shape shape) {

        int i = getCurrentShapesList().indexOf(shape);

        Shape replacementShape = Shape.of(getShapeChoice(), (Double) getSize(), getColor(), shape.getCoordinates());

        Command editCommand = new EditCommand(shape, replacementShape, this, i);
        editCommand.execute();
        getUndoList().add(editCommand);
        getRedoList().clear();
    }

    Optional<Shape> findSelectedShape(CanvasCoordinates coordinates) {
        return getCurrentShapesList().stream()
                .filter(shape -> shape.isCoveringCoordinates(coordinates))
                .reduce((a,b) -> b);
    }

    void addShapeToCurrent(Shape shapeToAdd) {

        Command addCommand = new AddCommand(shapeToAdd, this);
        addCommand.execute();
        getUndoList().add(addCommand);
        getRedoList().clear();
    }

    void replaceSelectedShape(CanvasCoordinates coordinates) {

        var selectedShape = findSelectedShape(coordinates);
        selectedShape.ifPresent(this::replaceShape);
    }

    void updateUndoAvailability() {
        setUndoIsUnavailable(getUndoList().isEmpty());
    }

    void updateRedoAvailability() {
        setRedoIsUnavailable(getRedoList().isEmpty());
    }

    public Shape createShape(CanvasCoordinates coordinates) {
        return Shape.of(getShapeChoice(), (Double) getSize(), getColor(), coordinates);
    }

    void setupServerConnection() {
        try {
            Socket socket = new Socket("localhost", 8000);
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            startServerListener(reader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isConvertibleToShape(String text) {
        return Pattern.compile("data-dsapp=\"(.+)\"").matcher(text).find();
    }

    private void startServerListener(BufferedReader reader) {

        Thread serverListenerThread = new Thread(() -> {
            while(true) {
                try {
                    String serverMessage = reader.readLine();
                    if (isConvertibleToShape(serverMessage)) {
                        Platform.runLater(() -> {
                            addShapeToCurrent(Shape.fromServerString(serverMessage));
                        });
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        serverListenerThread.setDaemon(true);
        serverListenerThread.start();
    }
}
