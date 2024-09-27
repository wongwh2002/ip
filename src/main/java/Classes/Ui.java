package Classes;

public class Ui {
    public void print(String message) {
        System.out.println("\t" + message);
    }

    public void printSeparator() {
        print(Constants.SEPARATOR);
    }

    public void printWithSeparators(String message) {
        printSeparator();
        print(message);
        printSeparator();
    }

    public void printGreeting() {
        printSeparator();
        print("Hello! I'm Weng");
        print("What can I do for you?");
        printSeparator();
    }

    public void printGoodbye() {
        printSeparator();
        print("Bye. Hope to see you again soon!");
        printSeparator();
    }
}