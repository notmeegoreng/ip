package clue.tasks;

import java.time.LocalDateTime;

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
    public String save(String separator) {
        return String.join(separator, "D", this.name, this.by.toString());
    }

    public static Task construct(String... args) {
        assert args.length == 3;
        return new Deadline(args[1], tryParseDate(args[2]));
    }
}
