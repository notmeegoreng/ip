import java.util.Scanner;

public class Clue {
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
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (scanner != null && scanner.hasNextLine()) {
            String[] input = scanner.nextLine().split(" ", 2);
            System.out.println(separator);
            String command = input[0];
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
                    int idx;
                    try {
                        idx = Integer.parseInt(input[1]) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task number! Please provide a positive integer!");
                        break;
                    }
                    if (idx < 0) {
                        System.out.println("Invalid task number! Please provide a positive integer!");
                    } else if (idx >= taskCount) {
                        System.out.println("Invalid task number! Not enough recorded tasks!");
                    } else {
                        Task t = tasks[idx];
                        t.setDone(true);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.print("\t");
                        System.out.println(t);
                    }
                }
                case "unmark" -> {
                    int idx;
                    try {
                        idx = Integer.parseInt(input[1]) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid task number! Please provide a positive integer!");
                        break;
                    }
                    if (idx < 0) {
                        System.out.println("Invalid task number! Please provide a positive integer!");
                    } else if (idx >= taskCount) {
                        System.out.println("Invalid task number! Not enough recorded tasks!");
                    } else {
                        Task t = tasks[idx];
                        t.setDone(false);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.print("\t");
                        System.out.println(t);
                    }
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
}
