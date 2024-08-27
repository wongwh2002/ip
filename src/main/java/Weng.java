import java.util.Scanner;

public class Weng {
    public static void main(String[] args) {
        greeting();
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.equals("bye")) {
                goodbye();
                break;
            } else {
                echo(line);
            }
        }
    }

    public static void print(String words) {
        System.out.println("\t" + words);
    }

    public static void echo(String word) {
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
