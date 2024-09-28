package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;

public abstract class Command {
    /**
     * Executes the command.
     *
     * @param taskList the task list to be managed
     * @param ui       the UI to be used for interactions
     * @param storage  the storage to be used for saving and loading tasks
     * @throws Exception if an exception occurs
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws Exception;
}
