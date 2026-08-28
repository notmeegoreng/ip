import java.util.Scanner;

public class Clue {
    public static void main(String[] args) {
        String banner =
                "  _____ _      _    _ _____ \n"
                + " / ____| |    | |  | |  ___|\n"
                + "| |    | |    | |  | | |___ \n"
                + "| |    | |    | |  | |  ___| \n"
                + "| |____| |____| |__| | |___ \n"
                + " \\_____|______|\\____/|_____| \n";s
        String separator = "____________________________________________________________";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println("Hello! I'm Clue.");
        System.out.println("What can I do for you?");
        System.out.println(separator);

        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(separator);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            if (command.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
            } else if (taskCount < tasks.length) {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println(" added: " + command);
            }

            System.out.println(separator);
        }
    }
}
