import java.util.Scanner;

public class Weng {
    private static String[] userList = new String[100];
    private static int numItem = 0;

    public static String[] getUserList() {
        return userList;
    }

    public static void setUserList(String[] userList) {
        Weng.userList = userList;
    }

    public static void setIndexInList(String word, int index) {
        Weng.userList[index] = word;
    }

    public static int getNumItem() {
        return numItem;
    }

    public static void setNumItem(int numItem) {
        Weng.numItem = numItem;
    }

    public static void addToList(String item) {
        setIndexInList(item, getNumItem());
        setNumItem(getNumItem() + 1);
        echo("added: " + item);
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
                printList();
                break;
            default:
                addToList(line);
            }
        }
    }

    public static void printList() {
        print_line();
        for (int i = 0; i < getNumItem(); i++) {
            print(String.format("%d. %s", i, getUserList()[i]));
        }
        print_line();
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
