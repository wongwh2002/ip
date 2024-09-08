import duke.Deadline;
import duke.Event;
import duke.Task;
import duke.Todo;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Weng {
    public static final String SEPARATOR = "____________________________________________________________";
    private static Task[] tasks = new Task[100];
    private static int totalNumTasks = 0;

    public static void main(String[] args) {
        printGreeting();
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
        addAndPrintHandler(new Todo(description));
    }

    public static void addEvent(String[] input) {
        addAndPrintHandler(new Event(input));
    }

    public static void addDeadline(String[] input) {

        addAndPrintHandler(new Deadline(input));
    }

    public static void setIndexInTask(Task task, int index) {
        Weng.tasks[index] = task;
    }

    public static int getTotalNumTasks() {
        return totalNumTasks;
    }

    public static void setTotalNumTasks(int totalNumTasks) {
        Weng.totalNumTasks = totalNumTasks;
    }

    private static void addAndPrintHandler(Task newTask) {
        setIndexInTask(newTask, getTotalNumTasks());
        setTotalNumTasks(getTotalNumTasks() + 1);
        print("Got it. I've added this task:");
        print("\t" + newTask.toString());
        print("Now you have " + getTotalNumTasks() + " tasks in the list.");
    }

    public static void print(String words) {
        System.out.println("\t" + words);
    }

    public static void printSeparator() {
        print(SEPARATOR);
    }

    private static void inputHandler() {
        Scanner scanner = new Scanner(System.in);
        String line;
        loop:
        while (true) {
            line = scanner.nextLine();
            String[] currLine = line.split(" ");
            printSeparator();
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
            printSeparator();
        }
        printSeparator();
    }

    public static void unmarkTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (getTotalNumTasks() < index) {
            print("Index out of range");
        } else if (!getTasks()[index].isDone()) {
            print("Already unmarked");
        } else {
            print("Nice! I've marked this task as not done yet:");
            getTasks()[index].setDone(false);
        }
        print(getTasks()[index].toString());
    }

    public static void markTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (getTotalNumTasks() < index) {
            print("Index out of range");
        } else {
            if (getTasks()[index].isDone()) {
                print("Already marked");
            } else {
                print("Nice! I've marked this task as done:");
                getTasks()[index].setDone(true);
            }
            print(getTasks()[index].toString());
        }
    }

    public static String formatPrintTask(Task task) {
        return String.format("[%s] %s", task.getStatusIcon(), task.getDescription());
    }

    public static void listTasks() {
        print("Here are the tasks in your list:");
        for (int i = 0; i < getTotalNumTasks(); i++) {
            print(String.format("%d. %s", i, getTasks()[i]));
        }
    }

    public static void printGreeting() {
        printSeparator();
        print("Hello! I'm Weng");
        print("What can I do for you?");
        printSeparator();
    }

    public static void goodbye() {
        print("Bye. Hope to see you again soon!");
    }
}
