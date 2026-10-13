package clue.commands;

import clue.components.TaskList;
import clue.tasks.Task;

public class UnmarkCommand extends TaskActionWithIndexCommand {
    public static final String name = "unmark";

    public UnmarkCommand(TaskList tasks) {
        super(tasks,
                "which task to unmark",
                "OK, I've marked this task as unfinished::");
    }

    @Override
    protected void action(Task task, int index) {
        task.setDone(false);
    }
}
