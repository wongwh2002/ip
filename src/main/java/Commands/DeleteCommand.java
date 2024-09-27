package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;

public class DeleteCommand extends Command {
    private final String[] input;

    public DeleteCommand(String[] input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.deleteTask(input);
    }
}