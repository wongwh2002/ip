import duke.Deadline;
import duke.Event;
import duke.Task;
import duke.Todo;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Weng {
    private static Task[] tasks = new Task[100];
    private static int numItem = 0;

    public static void main(String[] args) {
        greeting();
        String line;
        inputHandler();
    }

    public static Task[] getTasks() {
        return tasks;
    }

    public static void setTasks(Task[] tasks) {
        Weng.tasks = tasks;
    }

    public static void addTodo(String[] currLine) {
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, currLine.length));
        setIndexInTask(new Todo(description), getNumItem());
        addAndPrintHandler(new Todo(description));
    }

    public static void addEvent(String[] currLine) {
        int fromIndex = Arrays.asList(currLine).indexOf("/from");
        int toIndex = Arrays.asList(currLine).indexOf("/to");
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, fromIndex));
        String from = String.join(" ", Arrays.copyOfRange(currLine, fromIndex + 1, toIndex));
        String to = String.join(" ", Arrays.copyOfRange(currLine, toIndex + 1, currLine.length));
        addAndPrintHandler(new Event(description, from, to));
    }

    public static void addDeadline(String[] currLine) {
        int byIndex = Arrays.asList(currLine).indexOf("/by");
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, byIndex));
        String by = String.join(" ", Arrays.copyOfRange(currLine, byIndex + 1, currLine.length));
        addAndPrintHandler(new Deadline(description, by));
    }

    public static void setIndexInTask(Task task, int index) {
        Weng.tasks[index] = task;
    }

    public static int getNumItem() {
        return numItem;
    }

    public static void setNumItem(int numItem) {
        Weng.numItem = numItem;
    }

    private static void addAndPrintHandler(Task newTask) {
        setIndexInTask(newTask, getNumItem());
        setNumItem(getNumItem() + 1);
        print("Got it. I've added this task:");
        System.out.println("\t\t" + newTask);
        print("Now you have " + getNumItem() + " tasks in the list.");
    }

    public static void print(String words) {
        System.out.println("\t" + words);
    }

    public static void print_line() {
        print("____________________________________________________________");
    }

    private static void inputHandler() {
        Scanner scanner = new Scanner(System.in);
        String line;
        loop:
        while (true) {
            line = scanner.nextLine();
            String[] currLine = line.split(" ");
            print_line();
            switch (currLine[0]) {
            case "bye":
                goodbye();
                break loop;
            case "list":
                listTasks();
                break;
            case "todo":
                addTodo(currLine);
                break;
            case "deadline":
                addDeadline(currLine);
                break;
            case "event":
                addEvent(currLine);
                break;
            case "unmark":
                unmarkTask(currLine);
                break;
            case "mark":
                markTask(currLine);
                break;
            default:
                print("Invalid command");
                break;
            }
            print_line();
        }
        print_line();
    }

    public static void unmarkTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (getNumItem() < index) {
            print("Index out of range");
        } else {
            if (!getTasks()[index].isDone()) {
                print("Already unmarked");
            } else {
                print("Nice! I've marked this task as not done yet:");
                getTasks()[index].setDone(false);
            }
            print(formatPrintTask(getTasks()[index]));
        }
    }

    public static void markTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (getNumItem() < index) {
            print("Index out of range");
        } else {
            if (getTasks()[index].isDone()) {
                print("Already marked");
            } else {
                print("Nice! I've marked this task as done:");
                getTasks()[index].setDone(true);
            }
            print(formatPrintTask(getTasks()[index]));
        }
    }

    public static String formatPrintTask(Task task) {
        return String.format("[%s] %s", task.getStatusIcon(), task.getDescription());
    }

    public static void listTasks() {
        print("Here are the tasks in your list:");
        for (int i = 0; i < getNumItem(); i++) {
            print(String.format("%d. %s", i, getTasks()[i]));
        }
    }

    public static void greeting() {
        print_line();
        print("Hello! I'm Weng");
        print("What can I do for you?");
        print_line();
    }

    public static void goodbye() {
        print("Bye. Hope to see you again soon!");
    }
}
