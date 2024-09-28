package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;

public class DeleteCommand extends Command {
    private final String[] input;

    public DeleteCommand(String[] input) {
        this.input = input;
    }


    /**
     * Executes the DeleteCommand by deleting a task from the task list.
     *
     * @param taskList the task list to be managed
     * @param ui       the UI to be used for interactions
     * @param storage  the storage to be used for saving and loading tasks
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.deleteTask(input);
    }
}