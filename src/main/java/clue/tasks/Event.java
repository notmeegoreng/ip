package clue.tasks;

import clue.components.Storage;

import java.time.LocalDateTime;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

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

    public String save() {
        return String.join(Storage.SEPARATOR, "E", this.name,
                this.from.toString(), this.to.toString());
    }

    public static Task construct(String[] args) {
        return new Event(args[1], tryParseDate(args[2]), tryParseDate(args[3]));
    }
}
