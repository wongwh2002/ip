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

    /**
     * Constructs a TaskList with the specified UI.
     *
     * @param ui the UI to be used for interactions
     */
    public TaskList(Ui ui) {
        this.ui = ui;
    }

    /**
     * Handles multi-word commands and delegates to the appropriate method.
     *
     * @param currLine the command and its arguments
     * @throws DescriptionEmptyException if the description is empty
     * @throws MissingDatesException     if the date is missing
     * @throws IllegalCommandException   if the command is illegal
     */
    public void handleMultiWordCommands(String[] currLine) throws DescriptionEmptyException, MissingDatesException, IllegalCommandException {
        if (currLine.length < 2) {
            throw new DescriptionEmptyException("Please give correct input! " + currLine[0] + " is not enough!!");
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

    /**
     * Adds a To-do task from the input.
     *
     * @param currLine the input containing the task description
     */
    public void addTodoFromInput(String[] currLine) {
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, currLine.length));
        addAndPrintHandler(new Todo(description));
    }

    /**
     * Adds an Event task from the input.
     *
     * @param input the input containing the task description and dates
     * @throws MissingDatesException if the dates are missing
     */
    public void addEventFromInput(String[] input) throws MissingDatesException {
        int fromIndex = Arrays.asList(input).indexOf("/from");
        int toIndex = Arrays.asList(input).indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new MissingDatesException("Correct format: event <description> /from <yyyy/MM/dd> <HHmm> /to <yyyy/MM/dd> <HHmm>");
        }
        String description = String.join(" ", Arrays.copyOfRange(input, 1, fromIndex));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm");
        try {
            LocalDateTime fromDate = LocalDateTime.parse(input[fromIndex + 1] + " " + input[fromIndex + 2], formatter);
            LocalDateTime toDate = LocalDateTime.parse(input[toIndex + 1] + " " + input[toIndex + 2], formatter);
            addAndPrintHandler(new Event(description, fromDate, toDate));
        }
        catch (Exception e) {
            ui.printWithSeparators("Please provide a valid date in the format yyyy/MM/dd HHmm.");
        }
    }

    /**
     * Adds a Deadline task from the input.
     *
     * @param input the input containing the task description and date
     * @throws MissingDatesException if the date is missing
     */
    public void addDeadlineFromInput(String[] input) throws MissingDatesException {
        int byIndex = Arrays.asList(input).indexOf("/by");
        if (byIndex == -1) {
            throw new MissingDatesException("Correct format: deadline <description> /by <yyyy/MM/dd> <HHmm>");
        }
        String description = String.join(" ", Arrays.copyOfRange(input, 1, byIndex));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm");
        try {
            LocalDateTime byDate = LocalDateTime.parse(input[byIndex + 1] + " " + input[byIndex + 2], formatter);
            addAndPrintHandler(new Deadline(description, byDate));
        }
        catch (Exception e) {
            ui.printWithSeparators("Please provide a valid date in the format yyyy/MM/dd HHmm.");
        }
    }

    /**
     * Adds a To-do task from the file.
     *
     * @param description the task description
     * @param isDone      whether the task is done
     */
    public void addTodoFromFile(String description, boolean isDone) {
        tasks.add(new Todo(description, isDone));
    }

    /**
     * Adds an Event task from the file.
     *
     * @param desc     the task description
     * @param fromDate the start date and time
     * @param toDate   the end date and time
     * @param isDone   whether the task is done
     */
    public void addEventFromFile(String desc, String fromDate, String toDate, boolean isDone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime from = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime to = LocalDateTime.parse(toDate, formatter);
        tasks.add(new Event(desc, from, to, isDone));
    }

    /**
     * Adds a Deadline task from the file.
     *
     * @param desc   the task description
     * @param byDate the deadline date and time
     * @param isDone whether the task is done
     */
    public void addDeadlineFromFile(String desc, String byDate, boolean isDone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime by = LocalDateTime.parse(byDate, formatter);
        tasks.add(new Deadline(desc, by, isDone));
    }

    /**
     * Adds a task and prints a message.
     *
     * @param newTask the task to be added
     */
    private void addAndPrintHandler(Task newTask) {
        tasks.add(newTask);
        ui.printWithSeparators("Got it. I've added this task:\n\t" + newTask.toString() + "\n\tNow you have " + getTotalNumTasks() + " tasks in the list.");
    }

    /**
     * Deletes a task.
     *
     * @param currLine the input containing the index of the task
     */
    public void deleteTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks() || getTotalNumTasks() == 0) {
            throw new IndexOutOfBoundsException();
        }
        Task removedTask = tasks.remove(index);
        ui.printWithSeparators("Noted. I've removed this task:\n\t" + removedTask.toString() + "\n\tNow you have " + getTotalNumTasks() + " tasks in the list.");
    }

    /**
     * Unmarks a task as done.
     *
     * @param currLine the input containing the index of the task
     */
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

    /**
     * Marks a task as done.
     *
     * @param currLine the input containing the task index
     */
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

    /**
     * Lists all tasks in the task list.
     */
    public void listTasks() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < getTotalNumTasks(); i++) {
            sb.append(String.format("\n\t%d. %s", i, tasks.get(i)));
        }
        ui.printWithSeparators(sb.toString());
    }

    /**
     * Lists tasks on a specified date.
     *
     * @param currLine the input containing the date
     */
    public void listTasksOnDate(String[] currLine) {
        if (currLine.length < 2) {
            ui.printWithSeparators("Please provide a date in the format yyyy/MM/dd.");
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
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

    /**
     * Finds tasks by keyword.
     *
     * @param currLine the input containing the keyword
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

    /**
     * Returns the total number of tasks.
     *
     * @return the total number of tasks
     */
    public int getTotalNumTasks() {
        return tasks.size();
    }

    /**
     * Returns the list of tasks.
     *
     * @return the list of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}