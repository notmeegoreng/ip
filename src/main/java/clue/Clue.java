package clue;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import clue.commands.DeleteCommand;
import clue.commands.FindCommand;
import clue.commands.MarkCommand;
import clue.commands.UnmarkCommand;
import clue.components.Parser;
import clue.components.Storage;
import clue.components.TaskList;
import clue.tasks.Deadline;
import clue.tasks.Event;
import clue.tasks.Task;
import clue.tasks.Task.InvalidTaskException;
import clue.tasks.ToDo;
import clue.ui.Gui;
import clue.ui.Ui;
import javafx.application.Application;

/** Main class of the program. Registers the commands and runs the chatbot. */
public class Clue {
    private static final Storage storage = new Storage("./data.txt");
    private static final TaskList tasks = storage.load(new TaskList());

    private static final Parser parser = new Parser();

    static void main(String[] args) {
        register();

        // Command line based system
        // Ui ui = new Ui(parser, System.in, System.out);

        // GUI system

        try {
            // ui.listen();
            Application.launch(Gui.class, args);
        } finally {
            if (!storage.save(tasks)) {
                System.out.println("An error occurred when trying to save!");
            }
        }
    }

    /** Returns the current Parser object. */
    public static Parser getParser() {
        return parser;
    }

    // ==================== Command Registration ====================

    static void register() {
        // Simple commands (no arguments required)
        parser.register("bye", (ui, _) -> {
            ui.println("Bye. Hope to see you again soon!");
            return false;
        });
        parser.register("list", (ui, _) -> {
            tasks.list(ui);
            return true;
        });

        // Single-argument commands with validation
        parser.register(MarkCommand.name, new MarkCommand(tasks));
        parser.register(UnmarkCommand.name, new UnmarkCommand(tasks));
        parser.register(DeleteCommand.name, new DeleteCommand(tasks));

        // Task creation commands
        parser.register("todo", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us what this todo is called!");
                return true;
            }

            String todoName = args.get("").trim();
            if (todoName.isEmpty()) {
                ui.println("Please tell us what this todo is called!");
                return true;
            }

            try {
                tasks.addTask(ui, new ToDo(todoName));
            } catch (InvalidTaskException e) {
                ui.println("Error creating todo: " + e.getMessage());
            }
            return true;
        });

        parser.register("deadline", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us what this deadline is called!");
                return true;
            }

            String deadlineName = args.get("").trim();
            if (deadlineName.isEmpty()) {
                ui.println("Please tell us what this deadline is called!");
                return true;
            }

            String unparsedBy = args.get("by");
            if (unparsedBy == null || unparsedBy.trim().isEmpty()) {
                ui.println("Please provide a deadline date using the /by argument!");
                return true;
            }

            LocalDateTime by = parseDate(ui, unparsedBy);
            if (by != null) {
                try {
                    tasks.addTask(ui, new Deadline(deadlineName, by));
                } catch (InvalidTaskException e) {
                    ui.println("Error creating deadline: " + e.getMessage());
                }
            }
            return true;
        });

        parser.register("event", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us what this event is called!");
                return true;
            }

            String eventName = args.get("").trim();
            if (eventName.isEmpty()) {
                ui.println("Please tell us what this event is called!");
                return true;
            }

            String unparsedFrom = args.get("from");
            String unparsedTo = args.get("to");

            if (unparsedFrom == null || unparsedFrom.trim().isEmpty()) {
                ui.println("Please provide a start date using the /from argument!");
                return true;
            }

            if (unparsedTo == null || unparsedTo.trim().isEmpty()) {
                ui.println("Please provide an end date using the /to argument!");
                return true;
            }

            LocalDateTime from = parseDate(ui, unparsedFrom);
            LocalDateTime to = parseDate(ui, unparsedTo);

            if (from != null && to != null) {
                try {
                    tasks.addTask(ui, new Event(eventName, from, to));
                } catch (InvalidTaskException e) {
                    ui.println("Error creating event: " + e.getMessage());
                }
            }
            return true;
        });

        // Find command
        parser.register(FindCommand.name, new FindCommand(tasks));
    }

    // ==================== Utility Methods ====================

    static LocalDateTime parseDate(Ui ui, String unparsed) {
        if (unparsed == null || unparsed.trim().isEmpty()) {
            ui.println("Invalid date format! Please provide a valid date. Example: [2026-]12-31[ 12:30]");
            return null;
        }

        LocalDateTime out;
        try {
            out = LocalDateTime.parse(unparsed, Task.IN_FORMAT);
        } catch (DateTimeParseException e) {
            ui.println("Invalid date format! Example: [2026-]12-31[ 12:30]");
            return null;
        }
        return out;
    }
}
