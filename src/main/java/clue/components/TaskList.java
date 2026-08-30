package clue.components;

import clue.tasks.Task;

import java.util.ArrayList;

/**
 * A list of tasks with reporting to keep the user updated.
 */
public class TaskList extends ArrayList<Task> {
    /**
     * Add a task to the list, reporting to the given Ui object.
     */
    public void addTask(Ui ui, Task task) {
        add(task);
        ui.print("Noted. I've added this task:\n\t");
        ui.println(task);
        reportCount(ui);
    }

    /**
     * Outputs all items in the list to the given Ui object.
     */
    public void list(Ui ui) {
        if (isEmpty())  {
            ui.println("nothing here...");
        }
        for (int i = 1; i <= size(); i++) {
            ui.println(" " + i + ". " + get(i - 1));
        }
    }

    /**
     * Reports the count of items list to the given Ui object.
     */
    public void reportCount(Ui ui) {
        ui.printf(
                "Now, there %s %d task%s in the list.\n",
                size() == 1 ? "is": "are",
                size(),
                size() == 1 ? "" : "s");
    }
}
