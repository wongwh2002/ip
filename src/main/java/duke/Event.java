package duke;

import java.util.Arrays;

public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    public Event(String[] input) {
        super(input[0]);
        int fromIndex = Arrays.asList(input).indexOf("/from");
        int toIndex = Arrays.asList(input).indexOf("/to");
        setDescription(String.join(" ", Arrays.copyOfRange(input, 1, fromIndex)));
        this.from = String.join(" ", Arrays.copyOfRange(input, fromIndex + 1, toIndex));
        this.to = String.join(" ", Arrays.copyOfRange(input, toIndex + 1, input.length));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
