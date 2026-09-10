package clue.tasks;

import java.time.LocalDateTime;

/** An Event, storing a {@link from} and {@link to} time in addition to the base class. */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /** Constructs an Event with the given name and from and to times. */
    public Event(String name, LocalDateTime from, LocalDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from %s to %s)", super.toString(),
                displayDate(this.from), displayDate(this.to));
    }

    @Override
    public String save(String separator) {
        return String.join(separator, "E", this.name,
                this.from.toString(), this.to.toString());
    }

    public static Task construct(String... args) {
        assert args.length == 4;
        return new Event(args[1], tryParseDate(args[2]), tryParseDate(args[3]));
    }
}
