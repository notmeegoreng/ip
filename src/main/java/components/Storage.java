package components;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;
import tasks.ToDo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    private final File FILE;

    /**
     * Create a new Storage pointing at a filepath
     * @param filepath - the location where this class persists and loads data
     */
    public Storage(String filepath) {
        FILE = new File(filepath);
    }

    public ArrayList<Task> load() {
        ArrayList<Task> lst = new ArrayList<Task>();
        try (Scanner myReader = new Scanner(FILE)) {
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
            // file does not exist yet
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // we failed to implement the correct methods on classes in our CLASSES mapping
            throw new RuntimeException("Unimplemented methods needed for class loading!", e);
        }
        return lst;
    }

    public void save(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            for (Task t : tasks) {
                writer.write(t.save());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
