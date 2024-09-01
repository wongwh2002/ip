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
        String description = Arrays.toString(Arrays.copyOfRange(currLine, 1, currLine.length));
        setIndexInTask(new Todo(description), getNumItem());
        addAndPrintHandler(description);
    }

    public static void addEvent(String description, String from, String to) {
        setIndexInTask(new Event(description, from, to), getNumItem());
        addAndPrintHandler(description);
    }

    public static void setIndexInTask(Task task, int index) {
        Weng.tasks[index] = task;
    }

    public static int getNumItem() {
        return numItem;
    }

    private static void addAndPrintHandler(String description) {
        setNumItem(getNumItem() + 1);
        println("added: " + description);
    }

    public static void println(String word) {
        print_line();
        print(word);
        print_line();
    }

    public static void print_line() {
        print("____________________________________________________________");
    }

    public static void print(String words) {
        System.out.println("\t" + words);
    }

    public static void setNumItem(int numItem) {
        Weng.numItem = numItem;
    }

    public static void addDeadline(String description, String by) {
        setIndexInTask(new Deadline(description, by), getNumItem());
        addAndPrintHandler(description);
    }

    private static void inputHandler() {
        Scanner scanner = new Scanner(System.in);
        String line;
        loop:

        while (true) {
            line = scanner.nextLine();
            String[] currLine = line.split(" ");
            switch (currLine[0]) {
            case "bye":
                goodbye();
                break loop;
            case "list":
                listTasks();
                break;
            case "todo":
                addTodo(line);
                break;
            case "deadline":
                addTodo(line);
                break;
            case "event":
                addTodo(line);
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
        }
    }

    public static void unmarkTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (getNumItem() < index) {
            println("Index out of range");
        } else {
            print_line();
            if (!getTasks()[index].isDone()) {
                print("Already unmarked");
            } else {
                print("Nice! I've marked this task as not done yet:");
                getTasks()[index].setDone(false);
            }
            print(formatPrintTask(getTasks()[index]));
            print_line();
        }
    }

    public static void markTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (getNumItem() < index) {
            println("Index out of range");
        } else {
            print_line();
            if (getTasks()[index].isDone()) {
                print("Already marked");
            } else {
                print("Nice! I've marked this task as done:");
                getTasks()[index].setDone(true);
            }
            print(formatPrintTask(getTasks()[index]));
            print_line();
        }
    }

    public static String formatPrintTask(Task task) {
        return String.format("[%s] %s", task.getStatusIcon(), task.getDescription());
    }

    public static void listTasks() {
        print_line();
        print("Here are the tasks in your list:");
        for (int i = 0; i < getNumItem(); i++) {
            print(String.format("%d. %s", i, getTasks()[i]));
        }
        print_line();
    }

    public static void greeting() {
        print_line();
        print("Hello! I'm Weng");
        print("What can I do for you?");
        print_line();
    }

    public static void goodbye() {
        print_line();
        print("Bye. Hope to see you again soon!");
        print_line();
    }
}
