package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;

public class ExitCommand extends Command {
    @Override
    /**
     * Executes the ExitCommand by printing a goodbye message.
     *
     * @param taskList the task list to be managed
     * @param ui       the UI to be used for interactions
     * @param storage  the storage to be used for saving and loading tasks
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printGoodbye();
    }
}