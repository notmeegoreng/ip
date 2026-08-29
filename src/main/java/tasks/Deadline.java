package tasks;

import components.Storage;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String name, LocalDateTime by) {
        super(name);
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by %s)", super.toString(), displayDate(this.by));
    }

    public String save() {
        return String.join(Storage.SEPARATOR, "D", this.name, this.by.toString());
    }

    public static Task construct(String[] args) {
        return new Deadline(args[1], tryParseDate(args[2]));
    }
}
