package clue.components;

import clue.tasks.Task;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {
    public void addTask(Ui ui, Task task) {
        add(task);
        ui.print("Noted. I've added this task:\n\t");
        ui.println(task);
        reportCount(ui);
    }

    public void list(Ui ui) {
        if (isEmpty())  {
            ui.println("nothing here...");
        }
        for (int i = 1; i <= size(); i++) {
            ui.println(" " + i + ". " + get(i - 1));
        }
    }

    public void reportCount(Ui ui) {
        ui.printf(
                "Now, there %s %d task%s in the list.\n",
                size() == 1 ? "is": "are",
                size(),
                size() == 1 ? "" : "s");
    }
}
