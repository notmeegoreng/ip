package tasks;

public abstract class Task {
    protected final String name;
    private boolean done;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.done ? "X" : " ", this.name);
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public abstract String save();

    public static Task construct(String[] args) {
        return null;
    }
}
