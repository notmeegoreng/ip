package components;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements Ui.InputHandler {
    public interface Command {
        boolean run(Ui ui, Map<String, String> args);
    }

    public static final Command defaultCommand = (ui, _) -> {
        ui.println("uhh... sorry, I don't have a clue :(");
        return true;
    };

    public static final Pattern argumentRegex = Pattern.compile("/(.+?)");

    private final HashMap<String, Command> commands;

    public Parser() {
        this.commands = new HashMap<>();
    }

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
            Matcher matcher = argumentRegex.matcher(in).region(0, idx);
            String name = "";
            args = new HashMap<>();
            while (matcher.find()) {
                args.put(name, in.substring(idx, matcher.start()).trim());
                name = matcher.group(1);
                idx = matcher.end();
            }
            args.put(name, in.substring(idx));
        }

        return this.commands.getOrDefault(command, defaultCommand).run(ui, args);
    }
}
