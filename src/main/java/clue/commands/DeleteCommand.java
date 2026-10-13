package clue.commands;

import clue.commands.base.TaskActionWithIndexCommand;
import clue.components.TaskList;
import clue.tasks.Task;
import clue.ui.Ui;

/** A command to delete a task from the TaskList */
public class DeleteCommand extends TaskActionWithIndexCommand {
    public static final String name = "delete";

    /** Create a DeleteCommand operating on the given TaskList */
    public DeleteCommand(TaskList tasks) {
        super(tasks,
                "which task to delete",
                "Alright, deleted this task:");
    }

    @Override
    protected void action(Task task, int index) {
        task.setDone(true);
    }

    @Override
    protected void after(Ui ui) {
        tasks.reportCount(ui);
    }
}
