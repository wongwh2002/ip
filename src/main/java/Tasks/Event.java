package Tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime fromDate;
    protected LocalDateTime toDate;

    public Event(String description, LocalDateTime fromDate, LocalDateTime toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Event(String description, LocalDateTime fromDate, LocalDateTime toDate, boolean isDone) {
        super(description, isDone);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm a");
        return "[E]" + super.toString() + " (from: " + fromDate.format(formatter) + " to: " + toDate.format(formatter) + ")";
    }

    @Override
    public String toFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return "E | " + super.toFile() + " | " + fromDate.format(formatter) + " | " + toDate.format(formatter);
    }
}