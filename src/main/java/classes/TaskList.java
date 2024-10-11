package classes;

import commands.CommandType;
import exceptions.DescriptionEmptyException;
import exceptions.IllegalCommandException;
import exceptions.MissingDatesException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

/**
 * Manages the list of tasks and provides methods to manipulate them.
 */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Ui ui;

    /**
     * Constructs a TaskList object.
     *
     * @param ui the user interface for displaying messages
     */
    public TaskList(Ui ui) {
        this.ui = ui;
    }

    /**
     * Handles multi-word commands and delegates to the appropriate methods.
     *
     * @param currLine the command and its arguments
     * @throws DescriptionEmptyException if the description is empty
     * @throws MissingDatesException if the dates are missing
     * @throws IllegalCommandException if the command is illegal
     */
    public void handleMultiWordCommands(String[] currLine) throws DescriptionEmptyException, MissingDatesException, IllegalCommandException {
        if (currLine.length < 2) {
            throw new DescriptionEmptyException("Please give correct input! " + currLine[0] + " is not enough!!");
        }
        CommandType commandType = CommandType.valueOf(currLine[0].toUpperCase());
        switch (commandType) {
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
                throw new IllegalCommandException("I'm sorry, but I don't know what that means :-(");
        }
    }

    public void addTodoFromInput(String[] currLine) {
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, currLine.length));
        addAndPrintHandler(new Todo(description));
    }

    public void addEventFromInput(String[] input) throws MissingDatesException {
        int fromIndex = Arrays.asList(input).indexOf(Constants.FROM);
        int toIndex = Arrays.asList(input).indexOf(Constants.TO);
        if (fromIndex == -1 || toIndex == -1) {
            throw new MissingDatesException("Correct format: event <description> /from <yyyy/MM/dd> <HHmm> /to <yyyy/MM/dd> <HHmm>");
        }
        String description = String.join(" ", Arrays.copyOfRange(input, 1, fromIndex));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.INPUT_DATETIME_FORMAT);
        try {
            LocalDateTime fromDate = LocalDateTime.parse(input[fromIndex + 1] + " " + input[fromIndex + 2], formatter);
            LocalDateTime toDate = LocalDateTime.parse(input[toIndex + 1] + " " + input[toIndex + 2], formatter);
            addAndPrintHandler(new Event(description, fromDate, toDate));
        } catch (DateTimeParseException e) {
            ui.printWithSeparators("Please provide a valid date in the format " + Constants.INPUT_DATETIME_FORMAT + ".");
        }
    }

    public void addDeadlineFromInput(String[] input) throws MissingDatesException {
        int byIndex = Arrays.asList(input).indexOf(Constants.BY);
        if (byIndex == -1) {
            throw new MissingDatesException("Correct format: deadline <description> /by <yyyy/MM/dd> <HHmm>");
        }
        String description = String.join(" ", Arrays.copyOfRange(input, 1, byIndex));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.INPUT_DATETIME_FORMAT);
        try {
            LocalDateTime byDate = LocalDateTime.parse(input[byIndex + 1] + " " + input[byIndex + 2], formatter);
            addAndPrintHandler(new Deadline(description, byDate));
        } catch (DateTimeParseException e) {
            ui.printWithSeparators("Please provide a valid date in the format " + Constants.INPUT_DATETIME_FORMAT + ".");
        }
    }

    public void addTodoFromFile(String description, boolean isDone) {
        tasks.add(new Todo(description, isDone));
    }

    public void addEventFromFile(String desc, String fromDate, String toDate, boolean isDone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.FILE_DATETIME_FORMAT);
        LocalDateTime from = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime to = LocalDateTime.parse(toDate, formatter);
        tasks.add(new Event(desc, from, to, isDone));
    }

    public void addDeadlineFromFile(String desc, String byDate, boolean isDone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.FILE_DATETIME_FORMAT);
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
            ui.printWithSeparators(Constants.ERROR_INDEX_OUT_OF_RANGE);
            return;
        }
        if (!tasks.get(index).isDone()) {
            ui.printWithSeparators("Already unmarked");
            return;
        }
        tasks.get(index).setDone(false);
        ui.printWithSeparators("Nice! I've marked this task as not done yet:\n\t" + tasks.get(index).toString());
    }

    public void markTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks()) {
            ui.printWithSeparators(Constants.ERROR_INDEX_OUT_OF_RANGE);
            return;
        }
        if (tasks.get(index).isDone()) {
            ui.printWithSeparators("Already marked");
            return;
        }
        tasks.get(index).setDone(true);
        ui.printWithSeparators("Nice! I've marked this task as done:\n\t" + tasks.get(index).toString());
    }

    /**
     * Lists all tasks.
     */
    public void listTasks() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < getTotalNumTasks(); i++) {
            sb.append(String.format("\n\t%d. %s", i, tasks.get(i)));
        }
        ui.printWithSeparators(sb.toString());
    }

    /**
     * Lists tasks on a specific date.
     *
     * @param currLine the command and its arguments
     */
    public void listTasksOnDate(String[] currLine) {
        if (currLine.length < 2) {
            ui.printWithSeparators("Please provide a date in the format yyyy/MM/dd.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(currLine[1], formatter);
        StringBuilder sb = new StringBuilder("Tasks on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        listTasksOnDateHelper(sb, date);
        ui.printWithSeparators(sb.toString());
    }

    private void listTasksOnDateHelper(StringBuilder sb, LocalDate date) {
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
    }

    /**
     * Finds tasks by a keyword.
     *
     * @param currLine the command and its arguments
     */
    public void findTasksByKeyword(String[] currLine) {
        if (currLine.length < 2) {
            ui.printWithSeparators("Please provide a keyword to search for.");
            return;
        }
        String keyword = String.join(" ", Arrays.copyOfRange(currLine, 1, currLine.length));
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < getTotalNumTasks(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().contains(keyword)) {
                sb.append(String.format("\n\t%d. %s", i + 1, task));
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