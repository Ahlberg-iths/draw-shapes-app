package se.iths.ahlberg.drawshapesapp;

import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
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
        model.getCurrentShapesList().addListener((ListChangeListener<Shape>) (__ -> drawAllShapesOnCanvas()));
        model.getUndoList().addListener((ListChangeListener<Command>) (__ -> model.updateUndoAvailability()));
        undoButton.disableProperty().bind(model.undoIsUnavailableProperty());
        model.getRedoList().addListener((ListChangeListener<Command>) (__ -> model.updateRedoAvailability()));
        redoButton.disableProperty().bind(model.redoIsUnavailableProperty());
    }

    void drawAllShapesOnCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        model.getCurrentShapesList().forEach(shape -> shape.draw(graphicsContext));
    }

    public void onCanvasClicked(MouseEvent mouseEvent) {

        CanvasCoordinates coordinates = new CanvasCoordinates(mouseEvent.getX(), mouseEvent.getY());

        if (mouseEvent.isControlDown())
            model.replaceSelectedShape(coordinates);
        else {
            if (model.printWriter != null)
                model.printWriter.println(model.createShape(coordinates).toSVG());
            else
                model.addShapeToCurrent(model.createShape(coordinates));
        }
    }

    public void onSaveButtonClicked() {
        saveDrawingToFile();
    }

    private void saveDrawingToFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("SVG file (*.svg)","*.svg"));
            File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

            if (file != null){
                Path path = Path.of(file.getPath());
                Files.writeString(
                        path,
                        model.composeSVGElementWithCurrentShapes(canvas.getWidth(), canvas.getHeight())
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onUndoButtonClicked() {
        model.handleUndo();
    }

    public void onRedoButtonClicked() {
        model.handleRedo();
    }

    public void onConnectButtonClicked() {
        model.setupServerConnection();
    }
}
