package se.iths.ahlberg.drawshapesapp;

import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class AppController {

    Model model = new Model();

    public Canvas canvas;
    public Button undoButton;
    public Button redoButton;
    public Button connectButton;
    public Button saveButton;
    public Button updateButton;
    public ToggleButton selectModeButton;
    public ComboBox<ShapeChoice> shapeComboBox;
    public ColorPicker colorPicker;
    public Slider sizeSlider;
    private GraphicsContext graphicsContext;

    public void initialize() {
        colorPicker.valueProperty().bindBidirectional(model.colorProperty());
        sizeSlider.valueProperty().bindBidirectional(model.sizeProperty());
        shapeComboBox.valueProperty().bindBidirectional(model.shapeChoiceProperty());
        shapeComboBox.setItems(model.getShapeChoiceList());
        selectModeButton.selectedProperty().bindBidirectional(model.inSelectModeProperty());
        graphicsContext = canvas.getGraphicsContext2D();
        model.getCurrentShapesList().addListener((ListChangeListener<Shape>) aa -> drawShapesOnCanvas());
    }

    void drawShapesOnCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        model.getCurrentShapesList().forEach(shape -> shape.draw(graphicsContext));
    }

    public void handleCanvasClicked(MouseEvent mouseEvent) {

        CanvasCoordinates coordinates = new CanvasCoordinates(mouseEvent.getX(), mouseEvent.getY());

        if (model.isInSelectMode()) {
            //TODO:: check which shape is clicked
            //TODO:: put shape in some list with selected shapes ?
        } else {
            //TODO:: clear some list with selected shapes ? or do this on "in select mode button" click (off) instead?

            ShapeChoice shapeChoice = model.getShapeChoice();
            Number size = model.getSize();
            Color color = model.getColor();

            Shape newShape = Shape.of(shapeChoice, (Double)size, color, coordinates);
            model.addToCurrentShapesList(newShape);
        }
    }
}
