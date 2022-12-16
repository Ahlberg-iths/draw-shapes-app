package se.iths.ahlberg.drawshapesapp;

public class AddCommand implements Command {

    Shape shape;
    Model model;

    public AddCommand(Shape shape, Model model) {
        this.shape = shape;
        this.model = model;
    }

    @Override
    public void execute() {
        model.getCurrentShapesList().add(shape);
    }

    @Override
    public void undo() {
        model.getCurrentShapesList().remove(shape);
    }
}
