package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws Exception;
}
