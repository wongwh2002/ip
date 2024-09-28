import Classes.Ui;
import Classes.Storage;
import Classes.Parser;
import Classes.TaskList;

public class Zuke {
    /**
     * The main method to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList taskList = new TaskList(ui);
        Storage storage = new Storage(taskList, ui);
        Parser parser = new Parser(taskList, ui, storage);

        storage.loadTasks();
        ui.printGreeting();
        parser.handleUserInput();
        storage.saveTasks();
    }
}