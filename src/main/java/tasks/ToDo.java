package tasks;

import main.Saver;

public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public String save() {
        return String.join(Saver.SEPARATOR, "T", this.name);
    }

    public static Task construct(String[] args) {
        return new ToDo(args[1]);
    }
}
