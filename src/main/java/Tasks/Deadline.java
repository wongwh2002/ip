package Tasks;

import Exceptions.MissingDatesException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Deadline extends Task {
    public static final String BY = "/by";
    protected LocalDateTime byDate;

    public Deadline(String description, LocalDateTime byDate) {
        super(description);
        this.byDate = byDate;
    }

    public Deadline(String description, LocalDateTime byDate, boolean isDone) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        this.byDate = LocalDateTime.parse(String.join(" ", Arrays.copyOfRange(input, byIndex + 1, input.length)), formatter);
    }

    public LocalDateTime getByDate() {
        return byDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
        return "[D]" + super.toString() + " (by: " + byDate.format(formatter) + ")";
    }

    @Override
    public String toFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "D | " + super.toFile() + " | " + byDate.format(formatter);
    }
}