package clue;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import clue.components.Parser;
import clue.components.Storage;
import clue.components.TaskList;
import clue.ui.Gui;
import clue.ui.Ui;
import clue.tasks.Deadline;
import clue.tasks.Event;
import clue.tasks.Task;
import clue.tasks.ToDo;
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
        parser.register("mark", createTaskActionWithIndex(
                "which task to mark", (t, _) -> t.setDone(true),
                "Woohoo! I've marked this task as done:"));
        parser.register("unmark", createTaskActionWithIndex(
                "which task to unmark", (t, _) -> t.setDone(false),
                "OK, I've marked this task as unfinished:"));
        parser.register("delete", createTaskActionWithIndex(
                "which task to delete", (_, i) -> tasks.remove(i),
                "Alright, deleted this task:", tasks::reportCount)
        );

        // Task creation commands
        parser.register("todo", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us what this todo is called!");
                return true;
            }
            tasks.addTask(ui, new ToDo(args.get("")));
            return true;
        });
        parser.register("deadline", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us what this deadline is called!");
                return true;
            }
            LocalDateTime by = parseDate(ui, args.get("by"));
            if (by != null) {
                tasks.addTask(ui, new Deadline(args.get(""), by));
            }
            return true;
        });
        parser.register("event", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us what this event is called!");
                return true;
            }
            LocalDateTime from = parseDate(ui, args.get("from"));
            LocalDateTime to = parseDate(ui, args.get("to"));

            if (from != null && to != null) {
                tasks.addTask(ui, new Event(args.get(""), from, to));
            }
            return true;
        });
        
        // Search command
        parser.register("find", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please provide a keyword to search for!");
            } else {
                tasks.find(ui, args.get(""));
            }
            return true;
        });
    }

    // ==================== Helper Methods ====================

    /**
     * Creates a command handler that takes a task by index and does something with it.
     *
     * @param promptText the prompt text to show when index is missing
     * @param action the action to perform on the task
     * @param successMessage the message to show after successful action
     */
    private static Parser.Command createTaskActionWithIndex(
            String promptText, ObjIntConsumer<Task> action, String successMessage) {
        return createTaskActionWithIndex(promptText, action, successMessage, null);
    }

    /**
     * Creates a command handler that takes a task by index and does something with it.
     *
     * @param promptText the prompt text to show when index is missing
     * @param action the action to perform on the task
     * @param successMessage the message to show after successful action
     * @param after what to do after a successful action, or null if nothing
     */
    private static Parser.Command createTaskActionWithIndex(
            String promptText, ObjIntConsumer<Task> action, String successMessage, Consumer<Ui> after) {
        return (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us " + promptText + "!");
                return true;
            }
            int idx = getIndexFromInput(ui, args.get(""));
            if (idx != -1) {
                Task t = tasks.get(idx);
                action.accept(t, idx);
                ui.println(successMessage);
                ui.print("\t");
                ui.println(t);
                if (after != null) {
                    after.accept(ui);
                }
            }
            return true;
        };
    }

    // ==================== Utility Methods ====================

    static int getIndexFromInput(Ui ui, String unparsedInt) {
        int idx;
        try {
            idx = Integer.parseInt(unparsedInt) - 1;
        } catch (NumberFormatException e) {
            ui.println("Invalid task number! Please provide a positive integer!");
            return -1;
        }
        if (idx < 0) {
            ui.println("Invalid task number! Please provide a positive integer!");
        } else if (idx >= tasks.size()) {
            ui.println("Invalid task number! Not enough recorded tasks!");
        } else {
            return idx;
        }
        return -1;
    }

    static LocalDateTime parseDate(Ui ui, String unparsed) {
        LocalDateTime out;
        if (unparsed == null) {
            out = Task.defaultDate();
        } else {
            try {
                out = LocalDateTime.parse(unparsed, Task.IN_FORMAT);
            } catch (DateTimeParseException e) {
                ui.println("Invalid date format! Example: [2026-]12-31[ 12:30]");
                return null;
            }
        }
        return out;
    }
}
