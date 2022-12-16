package se.iths.ahlberg.drawshapesapp;

public class EditCommand implements Command {

    Shape initial;
    Shape transformed;
    Model model;
    int index;

    public EditCommand(Shape initial, Shape transformed, Model model, int index) {
        this.initial = initial;
        this.transformed = transformed;
        this.model = model;
        this.index = index;
    }

    @Override
    public void execute() {
        model.getCurrentShapesList().set(index, transformed);
    }

    @Override
    public void undo() {
        model.getCurrentShapesList().set(index, initial);
    }
}
