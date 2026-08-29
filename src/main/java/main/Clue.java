package main;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Clue {
    private static ArrayList<Task> tasks;

    public static void main(String[] args) {
        String banner = """
                          _____ _      _    _ _____\s
                         / ____| |    | |  | |  ___|
                        | |    | |    | |  | | |___\s
                        | |    | |    | |  | |  ___|\s
                        | |____| |____| |__| | |___\s
                         \\_____|______|\\____/|_____|\s
                        """;
        String separator = "____________________________________________________________";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Clue.");
        System.out.println("What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        tasks = Saver.load();

        while (scanner != null && scanner.hasNextLine()) {
            String in = scanner.nextLine();
            String[] input = in.split(" ", 2);
            String command = input[0];
            System.out.println(separator);
            switch (command) {
                case "bye" -> {
                    System.out.println("Bye. Hope to see you again soon!");
                    scanner = null;
                }
                case "list" -> {
                    if (tasks.isEmpty())  {
                        System.out.println("nothing here...");
                    }
                    for (int i = 1; i <= tasks.size(); i++) {
                        System.out.println(" " + i + ". " + tasks.get(i - 1));
                    }
                }
                case "mark" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us which task to mark!");
                        break;
                    }
                    int idx = getIndexFromInput(input[1]);
                    if (idx != -1) {
                        Task t = tasks.get(idx);
                        t.setDone(true);
                        System.out.println("Woohoo! I've marked this task as done:");
                        System.out.print("\t");
                        System.out.println(t);
                    }
                }
                case "unmark" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us which task to unmark!");
                        break;
                    }
                    int idx = getIndexFromInput(input[1]);
                    if (idx != -1) {
                        Task t = tasks.get(idx);
                        t.setDone(false);
                        System.out.println("OK, I've marked this task as unfinished:");
                        System.out.print("\t");
                        System.out.println(t);
                    }
                }
                case "delete" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us which task to delete!");
                        break;
                    }
                    Task t = tasks.remove(getIndexFromInput(input[1]));
                    System.out.print("Alright, deleted this task:\n\t");
                    System.out.println(t);
                    reportCount();
                }
                case "todo" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us what this todo is called!");
                        break;
                    }
                    addTask(new ToDo(input[1]));
                }
                case "deadline" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us what this deadline is called!");
                        break;
                    }
                    String[] parts = input[1].split(" /by ", 2);
                    try {
                        addTask(new Deadline(
                                parts[0],
                                LocalDateTime.parse(parts[1], Task.IN_FORMAT)));
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format! Example: [2026-]12-31[ 12:30]");
                    }
                }
                case "event" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us what this event is called!");
                        break;
                    }
                    String[] parts = input[1].split(" /from ", 2);
                    String[] parts2 = parts[1].split(" /to ", 2);
                    try {
                        addTask(new Event(
                                parts[0],
                                LocalDateTime.parse(parts2[0], Task.IN_FORMAT),
                                LocalDateTime.parse(parts2[1], Task.IN_FORMAT)));
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format! Example: [2026-]12-31[ 12:30]");
                    }
                }
                default -> System.out.println("uhh... sorry, I don't have a clue :(");
            }

            System.out.println(separator);
        }

        Saver.save(tasks);
    }

    static int getIndexFromInput(String unparsedInt) {
        int idx;
        try {
            idx = Integer.parseInt(unparsedInt) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number! Please provide a positive integer!");
            return -1;
        }
        if (idx < 0) {
            System.out.println("Invalid task number! Please provide a positive integer!");
        } else if (idx >= tasks.size()) {
            System.out.println("Invalid task number! Not enough recorded tasks!");
        } else {
            return idx;
        }
        return -1;
    }

    static void addTask(Task task) {
        tasks.add(task);
        System.out.print("Noted. I've added this task:\n\t");
        System.out.println(task);
        reportCount();
    }

    static void reportCount() {
        System.out.printf(
                "Now, there %s %d task%s in the list.\n",
                tasks.size() == 1 ? "is": "are",
                tasks.size(),
                tasks.size() == 1 ? "" : "s");
    }
}
