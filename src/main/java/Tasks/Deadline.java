package Tasks;

import Exceptions.MissingDatesException;

import java.util.Arrays;

public class Deadline extends Task {
    public static final String BY = "/by";
    protected String byDate;

    public Deadline(String description, String byDate) {
        super(description);
        this.byDate = byDate;
    }

    public Deadline(String description, String byDate, boolean isDone) {
        super(description, isDone);
        this.byDate = byDate;
    }

    public Deadline(String[] input) throws MissingDatesException {
        super(input[0]);
        int byIndex = Arrays.asList(input).indexOf(BY);
        if (byIndex == -1) {
            throw new MissingDatesException(BY);
        }
        setDescription(String.join(" ", Arrays.copyOfRange(input, 1, byIndex)));
        this.byDate = String.join(" ", Arrays.copyOfRange(input, byIndex + 1, input.length));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate + ")";
    }

    @Override
    public String toFile() {
        return "D | " + super.toFile() + " | " + byDate;
    }
}
