package tasks;

import main.Saver;

public class Deadline extends Task {
    private final String by;

    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.by);
    }

    public String save() {
        return String.join(Saver.SEPARATOR, "D", this.name, this.by);
    }

    public static Task construct(String[] args) {
        return new Deadline(args[1], args[2]);
    }
}
