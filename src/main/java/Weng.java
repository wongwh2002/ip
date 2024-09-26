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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Weng {
    public static final String SEPARATOR = "____________________________________________________________";
    public static final String FILE_PATH = "src/main/Data/tasks.txt";
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String[] LEGAL_COMMANDS = {"bye", "list", "todo", "deadline", "event", "unmark", "mark", "delete"};

    public static void main(String[] args) {
        readFile();
        printGreeting();
        inputVerification();
        writeFile();
    }

    // File handling methods
    private static void readFile() {
        try {
            print("Reading from file...");
            readFileHandler();
        } catch (FileNotFoundException e) {
            print("File not found");
        } catch (IllegalCommandException e) {
            print("Illegal command");
        } catch (MissingDatesException e) {
            print("Missing dates");
        }
    }

    private static void writeFile() {
        try {
            print("Writing to file...");
            writeFileHandler();
        } catch (IOException e) {
            print("Error writing to file");
        }
    }

    public static void readFileHandler() throws FileNotFoundException, IllegalCommandException, MissingDatesException {
        try {
            // Ensure the directory exists
            Files.createDirectories(Paths.get("src/main/Data"));

            // Ensure the file exists
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String[] currLine = s.nextLine().split("\\|");
                boolean isDone = currLine[1].trim().equals("1");
                switch (currLine[0].trim()) {
                case "T":
                    addTodo(currLine[2], isDone);
                    break;
                case "D":
                    addDeadline(currLine[2].trim(), currLine[3].trim(), isDone);
                    break;
                case "E":
                    addEvent(currLine[2].trim(), currLine[3].trim(), currLine[4].trim(), isDone);
                    break;
                default:
                    throw new IllegalCommandException();
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found and could not be created");
        }
    }

    public static void writeFileHandler() throws IOException {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            for (Task task : tasks) {
                fw.write(task.toFile() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Input handling methods
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

    // Task handling methods
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

    private static void addTodo(String currLine, boolean isDone) {
        tasks.add(new Todo(currLine, isDone));
    }

    private static void addEvent(String desc, String fromDate, String toDate, boolean isDone) {
        tasks.add(new Event(desc, fromDate, toDate, isDone));
    }

    private static void addDeadline(String desc, String byDate, boolean isDone) {
        tasks.add(new Deadline(desc, byDate, isDone));
    }

    private static void addAndPrintHandler(Task newTask) {
        tasks.add(newTask);
        print("Got it. I've added this task:");
        print("\t" + newTask.toString());
        print("Now you have " + getTotalNumTasks() + " tasks in the list.");
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

    // Utility methods
    public static void print(String words) {
        System.out.println("\t" + words);
    }

    public static void printSeparator() {
        print(SEPARATOR);
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

    public static int getTotalNumTasks() {
        return tasks.size();
    }
}