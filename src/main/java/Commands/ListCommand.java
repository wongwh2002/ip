package Commands;

import Classes.Ui;
import Classes.TaskList;
import Classes.Storage;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.listTasks();
    }
}