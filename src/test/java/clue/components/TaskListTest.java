package clue.components;

import clue.tasks.ToDo;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests searching for tasks in a {@link TaskList}. */
class TaskListTest {

    @Test
    void find_keyword_returnsMatchingTasksWithNumbers() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("buy milk"));
        tasks.add(new ToDo("return BOOK"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Ui ui = new Ui(System.in, output);

        tasks.find(ui, "book");

        String result = output.toString(StandardCharsets.UTF_8);
        assertTrue(result.contains("Here are the matching tasks in your list:"));
        assertTrue(result.contains("1.[T][ ] read book"));
        assertTrue(result.contains("2.[T][ ] return BOOK"));
        assertFalse(result.contains("buy milk"));
    }

    @Test
    void find_unknownKeyword_reportsNoMatches() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("read book"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Ui ui = new Ui(System.in, output);

        tasks.find(ui, "movie");

        assertTrue(output.toString(StandardCharsets.UTF_8).contains("No matching tasks found."));
    }
}
