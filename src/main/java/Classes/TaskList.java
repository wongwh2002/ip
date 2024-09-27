package Classes;

import Commands.CommandType;
import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

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
        default:
            throw new IllegalCommandException();
        }
    }

    public void addTodoFromInput(String[] currLine) {
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, currLine.length));
        addAndPrintHandler(new Todo(description));
    }

    public void addEventFromInput(String[] input) throws MissingDatesException {
        addAndPrintHandler(new Event(input));
    }

    public void addDeadlineFromInput(String[] input) throws MissingDatesException {
        addAndPrintHandler(new Deadline(input));
    }

    public void addTodoFromFile(String description, boolean isDone) {
        tasks.add(new Todo(description, isDone));
    }

    public void addEventFromFile(String desc, String fromDate, String toDate, boolean isDone) {
        tasks.add(new Event(desc, fromDate, toDate, isDone));
    }

    public void addDeadlineFromFile(String desc, String byDate, boolean isDone) {
        tasks.add(new Deadline(desc, byDate, isDone));
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

    public int getTotalNumTasks() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}