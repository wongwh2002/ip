package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;
import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;

public class AddCommand extends Command {
    private final String[] input;

    /**
     * Constructs an AddCommand with the specified input.
     *
     * @param input the input to be used
     */
    public AddCommand(String[] input) {
        this.input = input;
    }

    /**
     * Executes the AddCommand by adding a task to the task list.
     *
     * @param taskList the task list to be managed
     * @param ui       the UI to be used for interactions
     * @param storage  the storage to be used for saving and loading tasks
     * @throws DescriptionEmptyException if the description of the task is empty
     * @throws MissingDatesException     if the dates of the task are missing
     * @throws IllegalCommandException   if the command is illegal
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DescriptionEmptyException, MissingDatesException, IllegalCommandException {
        taskList.handleMultiWordCommands(input);
    }
}