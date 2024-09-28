package Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDateTime byDate;

    public Deadline(String description, LocalDateTime byDate) {
        super(description);
        this.byDate = byDate;
    }

    public Deadline(String description, LocalDateTime byDate, boolean isDone) {
        super(description, isDone);
        this.byDate = byDate;
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