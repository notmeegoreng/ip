package clue.tasks;

import clue.components.Storage;

/** A basic task, does not store anything on top of the base class. */
public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String save() {
        return String.join(Storage.SEPARATOR, "T", this.name);
    }

    public static Task construct(String... args) {
        return new ToDo(args[1]);
    }
}
