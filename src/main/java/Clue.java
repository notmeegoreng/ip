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
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(separator);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(separator);
                break;
            }

            System.out.println(" " + command);
            System.out.println(separator);
        }
    }
}
