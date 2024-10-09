package commands;

import classes.Ui;
import classes.TaskList;
import classes.Storage;

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
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < taskList.getTotalNumTasks(); i++) {
            sb.append(String.format("\n\t%d. %s", i, taskList.getTasks().get(i)));
        }
        ui.printWithSeparators(sb.toString());
    }
}