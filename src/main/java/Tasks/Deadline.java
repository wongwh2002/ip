package Tasks;

import Exceptions.MissingDatesException;

import java.util.Arrays;

public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    public Deadline(String[] input) throws MissingDatesException {
        super(input[0]);
        int byIndex = Arrays.asList(input).indexOf("/by");
        if (byIndex == -1) {
            throw new MissingDatesException("/by");
        }
        setDescription(String.join(" ", Arrays.copyOfRange(input, 1, byIndex)));
        this.by = String.join(" ", Arrays.copyOfRange(input, byIndex + 1, input.length));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
