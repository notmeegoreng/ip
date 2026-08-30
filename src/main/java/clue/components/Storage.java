package clue.components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import clue.tasks.Deadline;
import clue.tasks.Event;
import clue.tasks.Task;
import clue.tasks.ToDo;

/**
 * Handle persisting and loading from the filesystem.
 *
 */
public class Storage {
    public static final String SEPARATOR = "!%#";
    public static final Map<String, Class<? extends Task>> CLASSES = Map.of(
            "T", ToDo.class,
            "D", Deadline.class,
            "E", Event.class
    );

    private final File file;

    /**
     * Create a new Storage pointing at a filepath
     *
     * @param filepath - the location where this class persists and loads data
     */
    public Storage(String filepath) {
        file = new File(filepath);
    }

    public TaskList load() {
        TaskList lst = new TaskList();
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.split(SEPARATOR);
                Class<? extends Task> cls = CLASSES.get(parts[0]);
                if (cls == null) {
                    // corruption?
                    continue;
                }
                lst.add((Task) cls.getMethod("construct", String[].class).invoke(null, (Object) parts));
            }
        } catch (FileNotFoundException e) {
            // file does not exist yet, return default empty list
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // we failed to implement the correct methods on classes in our CLASSES mapping
            throw new RuntimeException("Unimplemented methods needed for class loading!", e);
        }
        return lst;
    }

    public boolean save(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task t : tasks) {
                writer.write(t.save());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
