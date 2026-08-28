public class Task {
    private String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
