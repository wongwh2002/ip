package Classes;

import Commands.AddCommand;
import Commands.ListCommand;
import Commands.DeleteCommand;
import Commands.ExitCommand;
import Commands.CommandType;

import java.util.Scanner;

public class Parser {
    private final TaskList taskList;
    private final Ui ui;
    private final Storage storage;

    /**
     * Constructs a Parser with the specified TaskList, UI, and Storage.
     *
     * @param taskList the task list to be managed
     * @param ui       the UI to be used for interactions
     * @param storage  the storage to be used for saving and loading tasks
     */
    public Parser(TaskList taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Handles user input and delegates to the appropriate command.
     */
    public void handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        boolean isLooping = true;

        while (isLooping) {
            String line = scanner.nextLine();
            String[] currLine = line.split(" ");
            try {
                switch (CommandType.valueOf(currLine[0].toUpperCase())) {
                case BYE:
                    new ExitCommand().execute(taskList, ui, storage);
                    isLooping = false;
                    break;
                case LIST:
                    new ListCommand().execute(taskList, ui, storage);
                    break;
                case DELETE:
                    new DeleteCommand(currLine).execute(taskList, ui, storage);
                    break;
                case ON:
                    taskList.listTasksOnDate(currLine);
                    break;
                case FIND:
                    taskList.findTasksByKeyword(currLine);
                    break;
                default:
                    new AddCommand(currLine).execute(taskList, ui, storage);
                    break;
                }
            } catch (IllegalArgumentException e) {
                ui.printWithSeparators("Error: no such Commands " + currLine[0].toUpperCase());
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.printWithSeparators("Error: missing arguments for " + currLine[0].toUpperCase());
            } catch (Exception e) {
                ui.printWithSeparators("Error: " + e.getMessage());
            }
        }
    }
}