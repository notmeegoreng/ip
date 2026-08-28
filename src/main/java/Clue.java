import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.util.Arrays;
import java.util.Scanner;

public class Clue {
    private static int taskCount;
    private static Task[] tasks;

    public static void main(String[] args) {
        String banner =
                "  _____ _      _    _ _____ \n"
                + " / ____| |    | |  | |  ___|\n"
                + "| |    | |    | |  | | |___ \n"
                + "| |    | |    | |  | |  ___| \n"
                + "| |____| |____| |__| | |___ \n"
                + " \\_____|______|\\____/|_____| \n";
        String separator = "____________________________________________________________";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Clue.");
        System.out.println("What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        tasks = new Task[100];
        taskCount = 0;

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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i]);
                    }
                }
                case "mark" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us which task to mark!");
                        break;
                    }
                    Task t = getTaskFromInput(input[1]);
                    if (t != null) {
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
                    Task t = getTaskFromInput(input[1]);
                    if (t != null) {
                        t.setDone(false);
                        System.out.println("OK, I've marked this task as unfinished:");
                        System.out.print("\t");
                        System.out.println(t);
                    }
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
                    addTask(new Deadline(parts[0], parts[1]));
                }
                case "event" -> {
                    if (input.length == 1) {
                        System.out.println("Please tell us what this event is called!");
                        break;
                    }
                    String[] parts = input[1].split(" /from ", 2);
                    String[] parts2 = parts[1].split(" /to ", 2);
                    addTask(new Event(parts[0], parts2[0], parts2[1]));
                }
                default -> {
                    if (taskCount < tasks.length) {
                        tasks[taskCount] = new Task(command);
                        taskCount++;
                        System.out.println(" added: " + command);
                    }
                }
            }

            System.out.println(separator);
        }
    }

    static Task getTaskFromInput(String unparsedInt) {
        int idx;
        try {
            idx = Integer.parseInt(unparsedInt) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number! Please provide a positive integer!");
            return null;
        }
        if (idx < 0) {
            System.out.println("Invalid task number! Please provide a positive integer!");
        } else if (idx >= taskCount) {
            System.out.println("Invalid task number! Not enough recorded tasks!");
        } else {
            return tasks[idx];
        }
        return null;
    }

    static void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
        System.out.print("Noted. I've added this task:\n\t%");
        System.out.println(task);
        System.out.printf("Now, there are %d task%s in the list.\n", taskCount, taskCount == 1 ? "" : "s");
    }
}
