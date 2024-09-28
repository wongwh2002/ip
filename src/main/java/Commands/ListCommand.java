package Commands;

import Classes.Ui;
import Classes.TaskList;
import Classes.Storage;

public class ListCommand extends Command {
    /**
     * Executes the ListCommand by listing all tasks in the task list.
     *
     * @param taskList the task list to be managed
     * @param ui       the UI to be used for interactions
     * @param storage  the storage to be used for saving and loading tasks
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.listTasks();
    }
}