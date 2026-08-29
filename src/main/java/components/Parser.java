package components;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Parser implements Ui.InputHandler {
    private final ArrayList<Task> tasks;

    public Parser(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public boolean handle(Ui ui, String in) {
        String[] input = in.split(" ", 2);
        String command = input[0];
        switch (command) {
            case "bye" -> {
                ui.println("Bye. Hope to see you again soon!");
                return false;
            }
            case "list" -> {
                if (tasks.isEmpty())  {
                    ui.println("nothing here...");
                }
                for (int i = 1; i <= tasks.size(); i++) {
                    ui.println(" " + i + ". " + tasks.get(i - 1));
                }
            }
            case "mark" -> {
                if (input.length == 1) {
                    ui.println("Please tell us which task to mark!");
                    break;
                }
                int idx = getIndexFromInput(ui, input[1]);
                if (idx != -1) {
                    Task t = tasks.get(idx);
                    t.setDone(true);
                    ui.println("Woohoo! I've marked this task as done:");
                    ui.print("\t");
                    ui.println(t);
                }
            }
            case "unmark" -> {
                if (input.length == 1) {
                    ui.println("Please tell us which task to unmark!");
                    break;
                }
                int idx = getIndexFromInput(ui, input[1]);
                if (idx != -1) {
                    Task t = tasks.get(idx);
                    t.setDone(false);
                    ui.println("OK, I've marked this task as unfinished:");
                    ui.print("\t");
                    ui.println(t);
                }
            }
            case "delete" -> {
                if (input.length == 1) {
                    ui.println("Please tell us which task to delete!");
                    break;
                }
                Task t = tasks.remove(getIndexFromInput(ui, input[1]));
                ui.print("Alright, deleted this task:\n\t");
                ui.println(t);
                reportCount(ui);
            }
            case "todo" -> {
                if (input.length == 1) {
                    ui.println("Please tell us what this todo is called!");
                    break;
                }
                addTask(ui, new ToDo(input[1]));
            }
            case "deadline" -> {
                if (input.length == 1) {
                    ui.println("Please tell us what this deadline is called!");
                    break;
                }
                String[] parts = input[1].split(" /by ", 2);
                try {
                    addTask(ui, new Deadline(
                            parts[0],
                            LocalDateTime.parse(parts[1], Task.IN_FORMAT)));
                } catch (DateTimeParseException e) {
                    ui.println("Invalid date format! Example: [2026-]12-31[ 12:30]");
                }
            }
            case "event" -> {
                if (input.length == 1) {
                    ui.println("Please tell us what this event is called!");
                    break;
                }
                String[] parts = input[1].split(" /from ", 2);
                String[] parts2 = parts[1].split(" /to ", 2);
                try {
                    addTask(ui, new Event(
                            parts[0],
                            LocalDateTime.parse(parts2[0], Task.IN_FORMAT),
                            LocalDateTime.parse(parts2[1], Task.IN_FORMAT)));
                } catch (DateTimeParseException e) {
                    ui.println("Invalid date format! Example: [2026-]12-31[ 12:30]");
                }
            }
            default -> ui.println("uhh... sorry, I don't have a clue :(");
        }
        return true;
    }

    int getIndexFromInput(Ui ui, String unparsedInt) {
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

    void addTask(Ui ui, Task task) {
        tasks.add(task);
        ui.print("Noted. I've added this task:\n\t");
        ui.println(task);
        reportCount(ui);
    }

    void reportCount(Ui ui) {
        ui.printf(
                "Now, there %s %d task%s in the list.\n",
                tasks.size() == 1 ? "is": "are",
                tasks.size(),
                tasks.size() == 1 ? "" : "s");
    }
}
