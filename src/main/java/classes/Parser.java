package classes;

import commands.*;
import exceptions.DescriptionEmptyException;
import exceptions.IllegalCommandException;
import exceptions.MissingDatesException;

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
                isLooping = processCommand(currLine);
            } catch (IllegalArgumentException e) {
                ui.printWithSeparators(Constants.ERROR_NO_SUCH_COMMAND + currLine[0].toUpperCase());
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.printWithSeparators(Constants.ERROR_MISSING_ARGUMENTS + currLine[0].toUpperCase());
            } catch (Exception e) {
                ui.printWithSeparators("Error: " + e.getMessage());
            }
        }
    }

    private boolean processCommand(String[] currLine) throws DescriptionEmptyException, IllegalCommandException, MissingDatesException {
        switch (CommandType.valueOf(currLine[0].toUpperCase())) {
            case BYE:
                new ExitCommand().execute(taskList, ui, storage);
                return false;
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
        return true;
    }
}