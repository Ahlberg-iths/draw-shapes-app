package se.iths.ahlberg.drawshapesapp;

import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppController {

    Model model = new Model();

    public Canvas canvas;
    public Button undoButton;
    public Button redoButton;
    public Button connectButton;
    public Button saveButton;
    public ComboBox<ShapeChoice> shapeComboBox;
    public ColorPicker colorPicker;
    public Slider sizeSlider;
    private GraphicsContext graphicsContext;

    public void initialize() {
        colorPicker.valueProperty().bindBidirectional(model.colorProperty());
        sizeSlider.valueProperty().bindBidirectional(model.sizeProperty());
        shapeComboBox.valueProperty().bindBidirectional(model.shapeChoiceProperty());
        shapeComboBox.setItems(model.getShapeChoiceList());
        graphicsContext = canvas.getGraphicsContext2D();
        model.getCurrentShapesList().addListener((ListChangeListener<Shape>) (__ -> drawShapesOnCanvas()));
        model.getUndoList().addListener((ListChangeListener<Command>) (__ -> updateUndoAvailability()));
        undoButton.disableProperty().bind(model.undoIsUnavailableProperty());
        model.getRedoList().addListener((ListChangeListener<Command>) (__ -> updateRedoAvailability()));
        redoButton.disableProperty().bind(model.redoIsUnavailableProperty());
    }

    private void updateUndoAvailability() {
        model.setUndoIsUnavailable(model.getUndoList().isEmpty());
    }

    private void updateRedoAvailability() {
        model.setRedoIsUnavailable(model.getRedoList().isEmpty());
    }

    void drawShapesOnCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        model.getCurrentShapesList().forEach(shape -> shape.draw(graphicsContext));
    }

    String composeSVGElementFromCurrentShapes() {

        StringBuilder svg = new StringBuilder();
        svg.append("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" ")
                .append("width=\"").append(canvas.getWidth()).append("\" ")
                .append("height=\"").append(canvas.getHeight()).append("\">");

        model.getCurrentShapesList().forEach(shape -> svg.append("\n\t").append(shape.toSVG()));

        return svg.append("\n</svg>").toString();
    }

    public void drawOnCanvas(MouseEvent mouseEvent) {

        CanvasCoordinates coordinates = new CanvasCoordinates(mouseEvent.getX(), mouseEvent.getY());

        if (mouseEvent.isControlDown())
            drawOnCanvasInSelectMode(coordinates);
        else
            addNewShape(coordinates);
    }

    private void drawOnCanvasInSelectMode(CanvasCoordinates coordinates) {
        model.getCurrentShapesList().stream()
                .filter(shape -> shape.isCoveringCoordinates(coordinates))
                .reduce((a,b) -> b)
                .ifPresent(this::replaceShape);
    }

    private void addNewShape(CanvasCoordinates coordinates) {
        ShapeChoice shapeChoice = model.getShapeChoice();
        Number size = model.getSize();
        Color color = model.getColor();

        Shape newShape = Shape.of(shapeChoice, (Double)size, color, coordinates);

        Command addCommand = new AddCommand(newShape, model);
        addCommand.execute();
        model.getUndoList().add(addCommand);
        model.getRedoList().clear();
    }

    private void replaceShape(Shape shape) {

        int i = model.getCurrentShapesList().indexOf(shape);
        Shape replacementShape = Shape.of(model.getShapeChoice(), (Double) model.getSize(), model.getColor(), shape.getCoordinates());

        Command editCommand = new EditCommand(shape, replacementShape, model, i);
        editCommand.execute();
        model.getUndoList().add(editCommand);
        model.getRedoList().clear();
    }

    public void handleSaveToFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("SVG file (*.svg)","*.svg"));
            File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

            if (file != null){
                Path path = Path.of(file.getPath());
                Files.writeString(path, composeSVGElementFromCurrentShapes());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleUndo() {
        Command command = model.getUndoList().remove(model.getUndoList().size() - 1);
        command.undo();
        model.getRedoList().add(command);
    }

    public void handleRedo() {
        Command command = model.getRedoList().remove(model.getRedoList().size() - 1);
        command.execute();
        model.getUndoList().add(command);
    }
}
