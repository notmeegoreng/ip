package clue.tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/** Task base class. Contains some constants useful for its children, and keeps track of name and completion status. */
public abstract class Task {
    public static final DateTimeFormatter IN_FORMAT = new DateTimeFormatterBuilder()
            .optionalStart()
            .appendPattern("yyyy-")
            .optionalEnd()
            .appendPattern("MM-dd")
            .optionalStart()
            .appendPattern(" HH:mm")
            .optionalEnd()
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
            .toFormatter();
    public static final DateTimeFormatter OUT_FORMAT = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM);
    public static final DateTimeFormatter OUT_DATE_FORMAT = DateTimeFormatter
            .ofPattern("dd/MM/yy");


    protected final String name;
    private boolean done;

    /** Constructs a task with the given name, and a default completion state of false. */
    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.done ? "X" : " ", this.name);
    }

    /** Serialisation of this task to a string. Used to reconstruct the task using {@link construct}. */
    public abstract String save();

    /** Recreates the task using parts returned by {@link save}. */
    public static Task construct(String... args) {
        return null;
    }

    static String displayDate(LocalDateTime date) {
        if (date.toLocalTime().equals(LocalTime.MIN)) {
            return date.format(OUT_DATE_FORMAT);
        }
        return date.format(OUT_FORMAT);
    }

    public static LocalDateTime defaultDate() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    }

    static LocalDateTime tryParseDate(String date) {
        try {
            return LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            return defaultDate();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
