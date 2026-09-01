package clue.components;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user commands and arguments and dispatches them to defined handlers.
 */
public class Parser implements Ui.InputHandler {
    /** Command response callback */
    @FunctionalInterface
    public interface Command {
        /**
         * Response to a specific user command.
         *
         * @param ui - Ui object for writing back to the user.
         * @param args - A mapping of /key value arguments.
         *               If nonempty, always contains a "" key for the argument without any name.
         * @return boolean for whether to continue running. If false, initiates shutdown of the program.
         */
        boolean run(Ui ui, Map<String, String> args);
    }

    /** Handler that is run when the user's command is unrecognized. */
    public static final Command DEFAULT_COMMAND = (ui, _) -> {
        ui.println("uhh... sorry, I don't have a clue :(");
        return true;
    };

    /** Regex to match for named arguments. */
    public static final Pattern ARGUMENT_REGEX = Pattern.compile("/(.+?)");

    private final HashMap<String, Command> commands;

    public Parser() {
        this.commands = new HashMap<>();
    }

    /**
     * Registers a handler for a given command name.
     *
     * @param name - Name of the command. When the user starts their input with this name, this command is run.
     * @param command - Callback when this command is activated.
     */
    public void register(String name, Command command) {
        this.commands.put(name, command);
    }

    @Override
    public boolean handle(Ui ui, String in) {
        in = in.trim();
        int idx = in.indexOf(' ');
        String command;
        Map<String, String> args;
        if (idx == -1) {
            command = in;
            args = Map.of();
        } else {
            command = in.substring(0, idx);
            Matcher matcher = ARGUMENT_REGEX.matcher(in).region(0, idx);
            String name = "";
            args = new HashMap<>();
            while (matcher.find()) {
                args.put(name, in.substring(idx, matcher.start()).trim());
                name = matcher.group(1);
                idx = matcher.end();
            }
            args.put(name, in.substring(idx).trim());
        }

        return this.commands.getOrDefault(command, DEFAULT_COMMAND).run(ui, args);
    }
}
