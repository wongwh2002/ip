package Tasks;

import Exceptions.MissingDatesException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Event extends Task {
    public static final String FROM = "/from";
    public static final String TO = "/to";
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

//    public Event(String[] input) throws MissingDatesException {
//        super(input[0]);
//        int fromIndex = Arrays.asList(input).indexOf(FROM);
//        if (fromIndex == -1) {
//            throw new MissingDatesException(FROM);
//        }
//        int toIndex = Arrays.asList(input).indexOf(TO);
//        if (toIndex == -1) {
//            throw new MissingDatesException(TO);
//        }
//
//        setDescription(String.join(" ", Arrays.copyOfRange(input, 1, fromIndex)));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
//        this.fromDate = LocalDateTime.parse(String.join(" ", Arrays.copyOfRange(input, fromIndex + 1, toIndex)), formatter);
//        this.toDate = LocalDateTime.parse(String.join(" ", Arrays.copyOfRange(input, toIndex + 1, input.length)), formatter);
//    }

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