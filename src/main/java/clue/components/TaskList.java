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

    /**
     * Prints tasks whose displayed text contains the supplied keyword.
     *
     * @param ui the interface used to display the matching tasks
     * @param keyword the case-insensitive text to search for
     */
    public void find(Ui ui, String keyword) {
        String searchTerm = keyword.toLowerCase();
        int matchNumber = 1;

        for (Task task : this) {
            if (task.getName().toLowerCase().contains(searchTerm)) {
                if (matchNumber == 1) {
                    ui.println("Here are the matching tasks in your list:");
                }
                ui.println(" " + matchNumber + "." + task);
                matchNumber++;
            }
        }
        if (matchNumber == 1) {
            ui.println("No matching tasks found.");
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
