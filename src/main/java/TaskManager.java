import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;
import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public void handleMultiWordCommands(String[] currLine) throws DescriptionEmptyException, MissingDatesException, IllegalCommandException {
        if (currLine.length < 2) {
            throw new DescriptionEmptyException(currLine[0]);
        }
        switch (Command.valueOf(currLine[0].toUpperCase())) {
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
        Weng.print("Got it. I've added this task:");
        Weng.print("\t" + newTask.toString());
        Weng.print("Now you have " + getTotalNumTasks() + " tasks in the list.");
    }

    public void deleteTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks() || getTotalNumTasks() == 0) {
            throw new IndexOutOfBoundsException();
        }
        Weng.print("Noted. I've removed this task:");
        Weng.print(tasks.get(index).toString());
        tasks.remove(index);
        Weng.print("Now you have " + getTotalNumTasks() + " tasks in the list.");
    }

    public void unmarkTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks()) {
            Weng.print("Index out of range");
        } else if (!tasks.get(index).isDone()) {
            Weng.print("Already unmarked");
        } else {
            tasks.get(index).setDone(false);
            Weng.print("Nice! I've marked this task as not done yet:");
            Weng.print(tasks.get(index).toString());
        }
    }

    public void markTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks()) {
            Weng.print("Index out of range");
        } else if (tasks.get(index).isDone()) {
            Weng.print("Already marked");
        } else {
            tasks.get(index).setDone(true);
            Weng.print("Nice! I've marked this task as done:");
            Weng.print(tasks.get(index).toString());
        }
    }

    public void listTasks() {
        Weng.print("Here are the tasks in your list:");
        for (int i = 0; i < getTotalNumTasks(); i++) {
            Weng.print(String.format("%d. %s", i, tasks.get(i)));
        }
    }

    public int getTotalNumTasks() {
        return tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}