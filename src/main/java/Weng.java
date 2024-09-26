import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;
import Tasks.Task;

import java.io.IOException;
import java.util.Scanner;

public class Weng {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        FileHandler fileHandler = new FileHandler(taskManager);

        fileHandler.readFile();
        printGreeting();
        inputVerification(taskManager);
        fileHandler.writeFile();
    }

    private static void inputVerification(TaskManager taskManager) {
        Scanner scanner = new Scanner(System.in);
        boolean isLooping = true;

        while (isLooping) {
            try {
                String line = scanner.nextLine();
                String[] currLine = line.split(" ");
                printSeparator();

                if (!Command.isLegalCommand(currLine[0])) {
                    throw new IllegalCommandException();
                }

                switch (Command.valueOf(currLine[0].toUpperCase())) {
                case BYE:
                    printGoodbye();
                    isLooping = false;
                    break;
                case LIST:
                    taskManager.listTasks();
                    break;
                default:
                    taskManager.handleMultiWordCommands(currLine);
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

    public static void print(String words) {
        System.out.println("\t" + words);
    }

    public static void printSeparator() {
        print(Constants.SEPARATOR);
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