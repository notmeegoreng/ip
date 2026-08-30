package clue.components;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class Ui extends PrintStream {
    public static final String banner = """
              _____ _      _    _ _____\s
             / ____| |    | |  | |  ___|
            | |    | |    | |  | | |___\s
            | |    | |    | |  | |  ___|\s
            | |____| |____| |__| | |___\s
             \\_____|______|\\____/|_____|\s
            """;
    public static final String separator = "____________________________________________________________";

    public interface InputHandler {
        boolean handle(Ui ui, String input);
    }

    private final InputStream in;
    private final InputHandler handler;

    public Ui(InputHandler handler, InputStream in, OutputStream out) {
        super(out, true);
        this.in = in;
        this.handler = handler;
    }

    public void printSeparatorLine() {
        println(separator);
    }

    void preamble() {
        printSeparatorLine();
        println(banner);
        println("Hello! I'm Clue.");
        println("What can I do for you?");
        printSeparatorLine();
    }

    public void listen() {
        preamble();
        Scanner scanner = new Scanner(in);
        boolean running = true;
        while (running && scanner.hasNextLine()) {
            String in = scanner.nextLine();

            printSeparatorLine();
            running = this.handler.handle(this, in);
            printSeparatorLine();
        }
    }
}
