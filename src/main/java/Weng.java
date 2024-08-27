import duke.Task;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Weng {
    private static Task[] tasks = new Task[100];
    private static int numItem = 0;

    public static int getNumItem() {
        return numItem;
    }

    public static void setNumItem(int numItem) {
        Weng.numItem = numItem;
    }

/*
    private static String[] userList = new String[100];
    public static String[] getUserList() {
        return userList;
    }
    public static void setUserList(String[] userList) {
        Weng.userList = userList;
    }
    public static void setIndexInList(String word, int index) {
        Weng.userList[index] = word;
    }
*/

    public static Task[] getTasks() {
        return tasks;
    }

    public static void setTasks(Task[] tasks) {
        Weng.tasks = tasks;
    }

    public static void setIndexInTask(Task task, int index) {
        Weng.tasks[index] = task;
    }

    public static void addToTasks(String description) {
        setIndexInTask(new Task(description), getNumItem());
        setNumItem(getNumItem() + 1);
        println("added: " + description);
    }

    public static void main(String[] args) {
        greeting();
        Scanner scanner = new Scanner(System.in);
        String line;
        loop:
        while (true) {
            line = scanner.nextLine();
            switch (line) {
            case "bye":
                goodbye();
                break loop;
            case "list":
                listTasks();
                break;
            default:
                if (line.contains("unmark")) {
                    unmarkTask(line);
                } else if (line.contains("mark")) {
                    markTask(line);
                } else {
                    addToTasks(line);
                }
            }
        }
    }

    public static void unmarkTask(String line) {
        String[] currLine = line.split(" ");
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

    public static void markTask(String line) {
        String[] currLine = line.split(" ");
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
            print(String.format("%d. %s", i, formatPrintTask(getTasks()[i])));
        }
        print_line();
    }

    public static void print(String words) {
        System.out.println("\t" + words);
    }

    public static void println(String word) {
        print_line();
        print(word);
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

    public static void print_line() {
        print("____________________________________________________________");
    }
}
