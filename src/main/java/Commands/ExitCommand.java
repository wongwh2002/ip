package Commands;

import Classes.Storage;
import Classes.TaskList;
import Classes.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.printGoodbye();
    }
}