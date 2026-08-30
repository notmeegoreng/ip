package clue.tasks;

import java.time.LocalDateTime;

import clue.components.Storage;

/** A Deadline, storing a {@link by} time on top of the base class. */
public class Deadline extends Task {
    private final LocalDateTime by;

    /** Constructs a Deadline with the given name and by time. */
    public Deadline(String name, LocalDateTime by) {
        super(name);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by %s)", super.toString(), displayDate(this.by));
    }

    @Override
    public String save() {
        return String.join(Storage.SEPARATOR, "D", this.name, this.by.toString());
    }

    public static Task construct(String[] args) {
        return new Deadline(args[1], tryParseDate(args[2]));
    }
}
