import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Weng {
    public static final String SEPARATOR = "____________________________________________________________";
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String[] LEGAL_COMMANDS = {"bye", "list", "todo", "deadline", "event", "unmark", "mark", "delete"};

    public static void main(String[] args) {
        printGreeting();
        inputVerification();
    }

    private static void inputVerification() {
        Scanner scanner = new Scanner(System.in);
        boolean isLooping = true;

        while (isLooping) {
            try {
                String line = scanner.nextLine();
                String[] currLine = line.split(" ");
                printSeparator();

                if (!isLegalCommand(currLine[0])) {
                    throw new IllegalCommandException();
                }

                switch (currLine[0]) {
                case "bye":
                    printGoodbye();
                    isLooping = false;
                    break;
                case "list":
                    listTasks();
                    break;
                default:
                    handleMultiWordCommands(currLine);
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                print("Index out of range");
            } catch (IllegalCommandException e) {
                print("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            } catch (DescriptionEmptyException e) {
                print("☹ OOPS!!! The description of a " + e.errorMessage + " cannot be empty.");
            } catch (MissingDatesException e) {
                print("☹ OOPS!!! The dates of a " + e.errorMessage + " cannot be empty.");
            } finally {
                printSeparator();
            }
        }
    }

    private static boolean isLegalCommand(String command) {
        return Arrays.asList(LEGAL_COMMANDS).contains(command);
    }

    private static void handleMultiWordCommands(String[] currLine) throws DescriptionEmptyException, MissingDatesException, IllegalCommandException {
        if (currLine.length < 2) {
            throw new DescriptionEmptyException(currLine[0]);
        }

        switch (currLine[0]) {
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
        case "delete":
            deleteTask(currLine);
            break;
        default:
            throw new IllegalCommandException();
        }
    }

    public static void addTodo(String[] currLine) {
        String description = String.join(" ", Arrays.copyOfRange(currLine, 1, currLine.length));
        addAndPrintHandler(new Todo(description));
    }

    public static void addEvent(String[] input) throws MissingDatesException {
        addAndPrintHandler(new Event(input));
    }

    public static void addDeadline(String[] input) throws MissingDatesException {
        addAndPrintHandler(new Deadline(input));
    }

    public static void setIndexInTask(Task task, int index) {
        tasks.set(index, task);
    }

    public static int getTotalNumTasks() {
        return tasks.size();
    }

    private static void addAndPrintHandler(Task newTask) {
        tasks.add(newTask);
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

    public static void deleteTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks() || getTotalNumTasks() == 0) {
            throw new IndexOutOfBoundsException();
        }
        print("Noted. I've removed this task:");
        print(tasks.get(index).toString());
        tasks.remove(index);
        print("Now you have " + getTotalNumTasks() + " tasks in the list.");
    }

    public static void unmarkTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks()) {
            print("Index out of range");
        } else if (!tasks.get(index).isDone()) {
            print("Already unmarked");
        } else {
            tasks.get(index).setDone(false);
            print("Nice! I've marked this task as not done yet:");
            print(tasks.get(index).toString());
        }
    }

    public static void markTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (index >= getTotalNumTasks()) {
            print("Index out of range");
        } else if (tasks.get(index).isDone()) {
            print("Already marked");
        } else {
            tasks.get(index).setDone(true);
            print("Nice! I've marked this task as done:");
            print(tasks.get(index).toString());
        }
    }

    public static void listTasks() {
        print("Here are the tasks in your list:");
        for (int i = 0; i < getTotalNumTasks(); i++) {
            print(String.format("%d. %s", i, tasks.get(i)));
        }
    }

    public static void printGreeting() {
        printSeparator();
        print("Hello! I'm Weng");
        print("What can I do for you?");
        printSeparator();
    }

    public static void printGoodbye() {
        print("Bye. Hope to see you again soon!");
    }

    public static void readFile() throws FileNotFoundException {
        String filePath = "data/tasks.txt";
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String[] currLine = s.nextLine().split(" | ");

        }
    }

    public static void writeFile() throws IOException {
        String filePath = "data/tasks.txt";
        FileWriter fw = new FileWriter(filePath);
        try {
            for (Task task : tasks) {
                fw.write(task.toString());
            }
        }
    }
}