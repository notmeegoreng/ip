package clue.ui;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Handles all user interaction, both input and output.
 */
public class Ui extends PrintStream {
    public static final String BANNER = """
              _____ _      _    _ _____\s
             / ____| |    | |  | |  ___|
            | |    | |    | |  | | |___\s
            | |    | |    | |  | |  ___|\s
            | |____| |____| |__| | |___\s
             \\_____|______|\\____/|_____|\s
            """;
    public static final String SEPARATOR = "____________________________________________________________";

    /** Interface for input handling callback */
    public interface InputHandler {
        /**
         * Callback for when user input is given.
         *
         * @param ui - The {@link Ui} object that received this input. Write back to it to respond to the user.
         * @param input - The text that the user gives us.
         * @return Whether to continue listening for input.
         */
        boolean handle(Ui ui, String input);
    }

    private final InputStream in;

    /**
     * Create a {@link Ui} object with the given streams.
     *
     * @param in - The stream to listen to for user input
     * @param out - The stream to return output to the user
     */
    public Ui(InputStream in, OutputStream out) {
        super(out, true);
        this.in = in;
    }

    /** Prints the default separator line to the output stream. */
    public void printSeparatorLine() {
        println(SEPARATOR);
    }

    void preamble() {
        printSeparatorLine();
        println(BANNER);
        println("Hello! I'm Clue.");
        println("What can I do for you?");
        printSeparatorLine();
    }

    /**
     * Begin listening for user input. This method will continue blocking until the input handler returns false.
     *
     * @param handler - Callback when user input is received.
     */
    public void listen(InputHandler handler) {
        preamble();
        Scanner scanner = new Scanner(in);
        boolean running = true;
        while (running && scanner.hasNextLine()) {
            String in = scanner.nextLine();

            printSeparatorLine();
            running = handler.handle(this, in);
            printSeparatorLine();
        }
    }
}
