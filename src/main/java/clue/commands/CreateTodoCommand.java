package clue.commands;

import java.util.Map;

import clue.commands.base.CreateTaskCommand;
import clue.components.TaskList;
import clue.tasks.ToDo;
import clue.ui.Ui;

/** A command to create new ToDos */
public class CreateTodoCommand extends CreateTaskCommand<ToDo> {
    public final static String name = "todo";
    /**
     * Construct a CreateTodoCommand that adds the created ToDos to the given TaskList
     *
     * @param tasks the TaskList to operate on
     */
    public CreateTodoCommand(TaskList tasks) {
        super(tasks, name);
    }

    @Override
    protected ToDo makeTask(Ui ui, Map<String, String> args, String name) {
        return new ToDo(name);
    }
}
