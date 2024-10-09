package commands;

import classes.Storage;
import classes.TaskList;
import classes.Ui;
import tasks.Task;

import static java.lang.Integer.parseInt;

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
        int index = parseInt(input[1]);
        if (index >= taskList.getTotalNumTasks() || taskList.getTotalNumTasks() == 0) {
            throw new IndexOutOfBoundsException();
        }
        Task removedTask = taskList.getTasks().remove(index);
        ui.printWithSeparators("Noted. I've removed this task:\n\t" + removedTask.toString() + "\n\tNow you have " + taskList.getTotalNumTasks() + " tasks in the list.");
    }
}