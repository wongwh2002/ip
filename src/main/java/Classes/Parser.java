package Classes;

import Commands.AddCommand;
import Commands.ListCommand;
import Commands.CommandType;

import java.util.Scanner;

public class Parser {
    private final TaskList taskList;
    private final Ui ui;
    private final Storage storage;

    public Parser(TaskList taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    public void handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        boolean isLooping = true;

        while (isLooping) {
            String line = scanner.nextLine();
            String[] currLine = line.split(" ");
            try {
                switch (CommandType.valueOf(currLine[0].toUpperCase())) {
                case BYE:
                    ui.printGoodbye();
                    isLooping = false;
                    break;
                case LIST:
                    new ListCommand().execute(taskList, ui, storage);
                    break;
                default:
                    new AddCommand(currLine).execute(taskList, ui, storage);
                    break;
                }
            } catch (IllegalArgumentException e) {
                ui.printWithSeparators("Error: No enum constant Commands.CommandType." + currLine[0].toUpperCase());
            } catch (Exception e) {
                ui.printWithSeparators("Error: " + e.getMessage());
            }
        }
    }
}