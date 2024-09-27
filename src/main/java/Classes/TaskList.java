package Classes;

import Commands.CommandType;
import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Ui ui;

    public TaskList(Ui ui) {
        this.ui = ui;
    }

    public void handleMultiWordCommands(String[] currLine) throws DescriptionEmptyException, MissingDatesException, IllegalCommandException {
        if (currLine.length < 2) {
            throw new DescriptionEmptyException(currLine[0]);
        }
        switch (CommandType.valueOf(currLine[0].toUpperCase())) {
        case TODO:
            addTodoFromInput(currLine);
            break;
        case DEADLINE:
            addDeadlineFromInput(currLine);
            break;
        case EVENT:
            addEventFromInput(currLine);
            break;
        case UNMARK:
            unmarkTask(currLine);
            break;
        case MARK:
            markTask(currLine);
            break;
        case DELETE:
            deleteTask(currLine);
            break;
        case ON:
            listTasksOnDate(currLine);
            break;
        default:
            throw new IllegalCommandException();
        }
    }

    public void addTodoFromInput(String[] currLine) {
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, currLine.length));
        addAndPrintHandler(new Todo(description));
    }

    public void addEventFromInput(String[] input) throws MissingDatesException {
        int fromIndex = Arrays.asList(input).indexOf("/from");
        int toIndex = Arrays.asList(input).indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new MissingDatesException("/from or /to");
        }
        String description = String.join(" ", Arrays.copyOfRange(input, 1, fromIndex));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime fromDate = LocalDateTime.parse(input[fromIndex + 1] + " " + input[fromIndex + 2], formatter);
        LocalDateTime toDate = LocalDateTime.parse(input[toIndex + 1] + " " + input[toIndex + 2], formatter);
        addAndPrintHandler(new Event(description, fromDate, toDate));
    }

    public void addDeadlineFromInput(String[] input) throws MissingDatesException {
        int byIndex = Arrays.asList(input).indexOf("/by");
        if (byIndex == -1) {
            throw new MissingDatesException("/by");
        }
        String description = String.join(" ", Arrays.copyOfRange(input, 1, byIndex));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime byDate = LocalDateTime.parse(input[byIndex + 1] + " " + input[byIndex + 2], formatter);
        addAndPrintHandler(new Deadline(description, byDate));
    }

    public void addTodoFromFile(String description, boolean isDone) {
        tasks.add(new Todo(description, isDone));
    }

    public void addEventFromFile(String desc, String fromDate, String toDate, boolean isDone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime from = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime to = LocalDateTime.parse(toDate, formatter);
        tasks.add(new Event(desc, from, to, isDone));
    }

    public void addDeadlineFromFile(String desc, String byDate, boolean isDone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime by = LocalDateTime.parse(byDate, formatter);
        tasks.add(new Deadline(desc, by, isDone));
    }

    private void addAndPrintHandler(Task newTask) {
        tasks.add(newTask);
        ui.printWithSeparators("Got it. I've added this task:\n\t" + newTask.toString() + "\n\tNow you have " + getTotalNumTasks() + " tasks in the list.");
    }

    public void deleteTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks() || getTotalNumTasks() == 0) {
            throw new IndexOutOfBoundsException();
        }
        Task removedTask = tasks.remove(index);
        ui.printWithSeparators("Noted. I've removed this task:\n\t" + removedTask.toString() + "\n\tNow you have " + getTotalNumTasks() + " tasks in the list.");
    }

    public void unmarkTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks()) {
            ui.printWithSeparators("Index out of range");
        } else if (!tasks.get(index).isDone()) {
            ui.printWithSeparators("Already unmarked");
        } else {
            tasks.get(index).setDone(false);
            ui.printWithSeparators("Nice! I've marked this task as not done yet:\n\t" + tasks.get(index).toString());
        }
    }

    public void markTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks()) {
            ui.printWithSeparators("Index out of range");
        } else if (tasks.get(index).isDone()) {
            ui.printWithSeparators("Already marked");
        } else {
            tasks.get(index).setDone(true);
            ui.printWithSeparators("Nice! I've marked this task as done:\n\t" + tasks.get(index).toString());
        }
    }

    public void listTasks() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < getTotalNumTasks(); i++) {
            sb.append(String.format("\n\t%d. %s", i, tasks.get(i)));
        }
        ui.printWithSeparators(sb.toString());
    }

    public void listTasksOnDate(String[] currLine) {
        if (currLine.length < 2) {
            ui.printWithSeparators("Please provide a date in the format yyyy-MM-dd.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(currLine[1], formatter);
        StringBuilder sb = new StringBuilder("Tasks on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        for (Task task : tasks) {
            if (task instanceof Deadline deadline) {
                if (deadline.getByDate().toLocalDate().equals(date)) {
                    sb.append("\n\t").append(task);
                }
            } else if (task instanceof Event event) {
                if (event.getFromDate().toLocalDate().equals(date) || event.getToDate().toLocalDate().equals(date)) {
                    sb.append("\n\t").append(task);
                }
            }
        }
        ui.printWithSeparators(sb.toString());
    }

    public int getTotalNumTasks() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}