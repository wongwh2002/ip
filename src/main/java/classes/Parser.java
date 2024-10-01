package classes;

import commands.AddCommand;
import commands.ListCommand;
import commands.DeleteCommand;
import commands.ExitCommand;
import commands.CommandType;

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
                ui.printWithSeparators(Constants.ERROR_NO_SUCH_COMMAND + currLine[0].toUpperCase());
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.printWithSeparators(Constants.ERROR_MISSING_ARGUMENTS + currLine[0].toUpperCase());
            } catch (Exception e) {
                ui.printWithSeparators("Error: " + e.getMessage());
            }
        }
    }
}