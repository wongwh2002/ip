package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;
import Exceptions.DescriptionEmptyException;
import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;

public class AddCommand extends Command {
    private final String[] input;

    public AddCommand(String[] input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DescriptionEmptyException, MissingDatesException, IllegalCommandException {
        taskList.handleMultiWordCommands(input);
    }
}