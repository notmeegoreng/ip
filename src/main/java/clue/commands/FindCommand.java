package clue.commands;

import java.util.Map;

import clue.components.TaskList;
import clue.components.Parser;
import clue.ui.Ui;

/**
 * Command to search tasks fuzzily for keywords.
 */
public class FindCommand implements Parser.Command {
    public static String name = "find";
    private TaskList tasks;

    public FindCommand(TaskList tasks) {
        this.tasks = tasks;
    }

    public boolean run(Ui ui, Map<String, String> args) {
        if (args.isEmpty()) {
            ui.println("Please provide a keyword to search for!");
            return true;
        }

        String keyword = args.get("").trim();
        if (keyword.isEmpty()) {
            ui.println("Please provide a valid keyword to search for!");
            return true;
        }

        tasks.find(ui, keyword);
        return true;
    }
}
