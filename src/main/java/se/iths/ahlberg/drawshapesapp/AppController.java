package se.iths.ahlberg.drawshapesapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

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

    public void initialize() {
        colorPicker.valueProperty().bindBidirectional(model.colorProperty());
        sizeSlider.valueProperty().bindBidirectional(model.sizeProperty());
        shapeComboBox.valueProperty().bindBidirectional(model.shapeChoiceProperty());
        shapeComboBox.setItems(model.getShapeChoiceList());
        selectModeButton.selectedProperty().bindBidirectional(model.inSelectModeProperty());
    }
}
