package clue.commands;

import clue.components.TaskList;
import clue.tasks.Task;

/** A command that unmarks a selected task, recording it as undone. */
public class UnmarkCommand extends TaskActionWithIndexCommand {
    public static final String name = "unmark";

    /** Create an UnmarkCommand that operates on the given TaskList */
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
