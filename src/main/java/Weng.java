import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Weng {
    public static final String SEPARATOR = "____________________________________________________________";
    private static Task[] tasks = new Task[100];
    private static int totalNumTasks = 0;

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

    public static void addEvent(String[] input) throws MissingDatesException {
        addAndPrintHandler(new Event(input));
    }

    public static void addDeadline(String[] input) throws MissingDatesException {
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

    public static void main(String[] args) {
        printGreeting();
        inputVerification();
    }

    private static void inputVerification() {
        Scanner scanner = new Scanner(System.in);
        String line;
        //LEGAL_COMMANDS only used in this method, so ill leave it here
        String[] LEGAL_COMMANDS = {"bye", "list", "todo", "deadline", "event", "unmark", "mark", "delete"};
        boolean isLooping = true;
        while (isLooping) {
            try {
                //hmm, not sure how to refactor this code effectively
                //do give comments if got any idea
                line = scanner.nextLine();
                String[] currLine = line.split(" ");
                printSeparator();

                if (Arrays.stream(LEGAL_COMMANDS).noneMatch(currLine[0]::equals)) {
                    //if the command is not in the list of legal commands
                    throw new IllegalCommandException();
                }

                switch (currLine[0]) {
                case "bye":
                    printGoodbye();
                    isLooping = false; //break loop
                    continue;
                case "list":
                    listTasks();
                    continue;
                }
                //have done the 1-word commands, checking for multi-word commands now
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
            } catch (IndexOutOfBoundsException e) {
                print("Index out of range");
            } catch (IllegalCommandException e) {
                print("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            } catch (DescriptionEmptyException e) {
                //probably need a separate exception for mark and unmark but ill do it next time
                print("☹ OOPS!!! The description of a " + e.errorMessage + " cannot be empty.");
            } catch (MissingDatesException e) {
                print("☹ OOPS!!! The dates of a " + e.errorMessage + " cannot be empty.");
            } finally {
                printSeparator();
            }
        }
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

    public static void deleteTask(String[] currLine) {
        int index = parseInt(currLine[1]);
        if (getTotalNumTasks() < index || getTotalNumTasks() == 0) {
            throw new IndexOutOfBoundsException();
        }
        print("Noted. I've removed this task:");
        print(getTasks()[index].toString());
        for (int i = index; i < getTotalNumTasks() - 1; i++) {
            setIndexInTask(getTasks()[i + 1], i);
        }
        setTotalNumTasks(getTotalNumTasks() - 1);
        print("Now you have " + getTotalNumTasks() + " tasks in the list.");
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

    public static void printGoodbye() {
        print("Bye. Hope to see you again soon!");
    }
}
