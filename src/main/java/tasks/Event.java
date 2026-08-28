package tasks;

import main.Saver;

public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }

    public String save() {
        return String.join(Saver.SEPARATOR, "E", this.name, this.from, this.to);
    }

    public static Task construct(String[] args) {
        return new Event(args[1], args[2], args[3]);
    }
}
