package tasks;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /*
        * Returns the string representation of the task in the format to be displayed to the user.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /*
        * Returns the string representation of the task in the format to be saved in the file.
     */
    @Override
    public String toFile() {
        return "T | " + super.toFile();
    }
}

