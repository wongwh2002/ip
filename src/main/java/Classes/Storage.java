package Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Tasks.Task;

public class Storage {
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructor for the Storage class.
     *
     * @param taskList the task list to be used
     * @param ui       the user interface to be used
     */
    public Storage(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Loads the tasks from the file in FILEPATH.
     */
    public void loadTasks() {
        ui.printWithSeparators("Loading tasks from file...");
        try {
            File file = new File(Constants.FILE_PATH);
            if (!file.exists()) {
                boolean hasCreatedFile = file.createNewFile();
                if (hasCreatedFile) {
                    ui.printWithSeparators("File not found, creating new file...");
                } else {
                    ui.printWithSeparators("Error creating new file");
                }
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] currLine = scanner.nextLine().split("\\|");
                boolean isDone = currLine[1].trim().equals("1");
                switch (currLine[0].trim()) {
                case "T":
                    taskList.addTodoFromFile(currLine[2], isDone);
                    break;
                case "D":
                    taskList.addDeadlineFromFile(currLine[2].trim(), currLine[3].trim(), isDone);
                    break;
                case "E":
                    taskList.addEventFromFile(currLine[2].trim(), currLine[3].trim(), currLine[4].trim(), isDone);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown task type");
                }
            }
        } catch (IOException e) {
            ui.printWithSeparators("Error loading tasks: " + e.getMessage());
        }
    }

    /**
     * Saves the tasks to the file in FILEPATH.
     */
    public void saveTasks() {
        ui.printWithSeparators("Saving tasks to file...");
        try (FileWriter fw = new FileWriter(Constants.FILE_PATH)) {
            for (Task task : taskList.getTasks()) {
                fw.write(task.toFile() + System.lineSeparator());
            }
        } catch (IOException e) {
            ui.printWithSeparators("Error saving tasks: " + e.getMessage());
        }
    }
}