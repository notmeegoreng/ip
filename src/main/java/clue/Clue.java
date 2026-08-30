package clue;

import clue.components.Parser;
import clue.components.Storage;
import clue.components.TaskList;
import clue.components.Ui;
import clue.tasks.Deadline;
import clue.tasks.Event;
import clue.tasks.Task;
import clue.tasks.ToDo;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Clue {
    private static final Storage storage = new Storage("./data.txt");
    private static final TaskList tasks = storage.load(new TaskList());

    static void main() {
        Parser parser = new Parser();
        register(parser);
        try (Ui ui = new Ui(parser, System.in, System.out)) {
            ui.listen();
        } finally {
            if (!storage.save(tasks)) {
                System.out.println("An error occurred when trying to save!");
            }
        }
    }

    static void register(Parser parser) {
        parser.register("bye", (ui, _) -> {
            ui.println("Bye. Hope to see you again soon!");
            return false;
        });
        parser.register("list", (ui, _) -> {
            tasks.list(ui);
            return true;
        });
        parser.register("mark", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us which task to mark!");
                return true;
            }
            int idx = getIndexFromInput(ui, args.get(""));
            if (idx != -1) {
                Task t = tasks.get(idx);
                t.setDone(true);
                ui.println("Woohoo! I've marked this task as done:");
                ui.print("\t");
                ui.println(t);
            }
            return true;
        });
        parser.register("unmark", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us which task to unmark!");
                return true;
            }
            int idx = getIndexFromInput(ui, args.get(""));
            if (idx != -1) {
                Task t = tasks.get(idx);
                t.setDone(false);
                ui.println("OK, I've marked this task as unfinished:");
                ui.print("\t");
                ui.println(t);
            }
            return true;
        });
        parser.register("delete", (ui, args) -> {
            if (args.isEmpty()) {
                ui.println("Please tell us which task to delete!");
                return true;
            }
            int idx = getIndexFromInput(ui, args.get(""));
            if (idx == -1) {
                return true;
            }
            Task t = tasks.remove(idx);
            ui.print("Alright, deleted this task:\n\t");
            ui.println(t);
            tasks.reportCount(ui);
            return true;
        });
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
    }

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
            ui.println("Invalid task number! Not enough recorded clue.tasks!");
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
