package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import tasks.Task;

/**
 * Handles the loading and saving of tasks to and from a file.
 */
public class Storage {
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructs a Storage object.
     *
     * @param taskList the task list to be managed
     * @param ui the user interface for displaying messages
     */
    public Storage(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Loads tasks from the file.
     */
    public void loadTasks() {
        ui.printWithSeparators(Constants.LOADING_TASKS_MESSAGE);
        File file = new File(Constants.FILE_PATH);
        if (!file.exists()) {
            handleFileNotFound(file);
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                processTaskLine(scanner.nextLine());
            }
        } catch (IOException e) {
            ui.printWithSeparators(Constants.ERROR_LOADING_TASKS_MESSAGE + e.getMessage());
        }
    }

    private void handleFileNotFound(File file) {
        try {
            if (file.createNewFile()) {
                ui.printWithSeparators(Constants.FILE_NOT_FOUND_MESSAGE);
            } else {
                ui.printWithSeparators(Constants.ERROR_CREATING_FILE_MESSAGE);
            }
        } catch (IOException e) {
            ui.printWithSeparators(Constants.ERROR_CREATING_FILE_MESSAGE + e.getMessage());
        }
    }

    private void processTaskLine(String line) {
        String[] currLine = line.split("\\|");
        boolean isDone = currLine[1].trim().equals(Constants.TASK_DONE);
        switch (currLine[0].trim()) {
            case Constants.TASK_TYPE_TODO:
                taskList.addTodoFromFile(currLine[2], isDone);
                break;
            case Constants.TASK_TYPE_DEADLINE:
                taskList.addDeadlineFromFile(currLine[2].trim(), currLine[3].trim(), isDone);
                break;
            case Constants.TASK_TYPE_EVENT:
                taskList.addEventFromFile(currLine[2].trim(), currLine[3].trim(), currLine[4].trim(), isDone);
                break;
            default:
                throw new IllegalArgumentException(Constants.ERROR_UNKNOWN_TASK_TYPE);
        }
    }

    /**
     * Saves tasks to the file.
     */
    public void saveTasks() {
        ui.printWithSeparators(Constants.SAVING_TASKS_MESSAGE);
        try (FileWriter fw = new FileWriter(Constants.FILE_PATH)) {
            for (Task task : taskList.getTasks()) {
                fw.write(task.toFile() + System.lineSeparator());
            }
        } catch (IOException e) {
            ui.printWithSeparators(Constants.ERROR_SAVING_TASKS_MESSAGE + e.getMessage());
        }
    }
}