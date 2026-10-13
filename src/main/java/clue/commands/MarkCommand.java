package clue.commands;

import clue.components.TaskList;
import clue.tasks.Task;

public class MarkCommand extends TaskActionWithIndexCommand {
    public static final String name = "mark";

    public MarkCommand(TaskList tasks) {
        super(tasks,
                "which task to mark",
                "Woohoo! I've marked this task as done:");
    }

    @Override
    protected void action(Task task, int index) {
        task.setDone(true);
    }
}
