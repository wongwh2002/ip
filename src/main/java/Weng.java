public class Weng {
    public static void main(String[] args) {
        print_line();
        greeting();
        print_line();
        goodbye();
        print_line();
    }

    public static void greeting() {
        System.out.println("Hello! I'm Weng");
        System.out.println("What can I do for you?");
    }

    public static void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void print_line() {
        System.out.println("____________________________________________________________");
    }
}
