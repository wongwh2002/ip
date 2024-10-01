package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import tasks.Task;

public class Storage {
    private final TaskList taskList;
    private final Ui ui;

    public Storage(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    public void loadTasks() {
        ui.printWithSeparators(Constants.LOADING_TASKS_MESSAGE);
        try {
            File file = new File(Constants.FILE_PATH);
            if (!file.exists()) {
                boolean hasCreatedFile = file.createNewFile();
                if (hasCreatedFile) {
                    ui.printWithSeparators(Constants.FILE_NOT_FOUND_MESSAGE);
                } else {
                    ui.printWithSeparators(Constants.ERROR_CREATING_FILE_MESSAGE);
                }
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] currLine = scanner.nextLine().split("\\|");
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
        } catch (IOException e) {
            ui.printWithSeparators(Constants.ERROR_LOADING_TASKS_MESSAGE + e.getMessage());
        }
    }

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