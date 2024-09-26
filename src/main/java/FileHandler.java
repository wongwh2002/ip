import Exceptions.IllegalCommandException;
import Exceptions.MissingDatesException;
import Tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileHandler {
    private final TaskManager taskManager;

    public FileHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void readFile() {
        try {
            Weng.print("Reading from file...");
            readFileHandler();
        } catch (FileNotFoundException e) {
            Weng.print("File not found");
        } catch (IllegalCommandException e) {
            Weng.print("Illegal command");
        } catch (MissingDatesException e) {
            Weng.print("Missing dates");
        }
    }

    public void writeFile() {
        try {
            Weng.print("Writing to file...");
            writeFileHandler();
        } catch (IOException e) {
            Weng.print("Error writing to file");
        }
    }

    private void readFileHandler() throws FileNotFoundException, IllegalCommandException, MissingDatesException {
        try {
            Files.createDirectories(Paths.get(Constants.DATA_DIRECTORY));

            File file = new File(Constants.FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String[] currLine = s.nextLine().split("\\|");
                boolean isDone = currLine[1].trim().equals("1");
                switch (currLine[0].trim()) {
                case "T":
                    taskManager.addTodoFromFile(currLine[2], isDone);
                    break;
                case "D":
                    taskManager.addDeadlineFromFile(currLine[2].trim(), currLine[3].trim(), isDone);
                    break;
                case "E":
                    taskManager.addEventFromFile(currLine[2].trim(), currLine[3].trim(), currLine[4].trim(), isDone);
                    break;
                default:
                    throw new IllegalCommandException();
                }
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found and could not be created");
        }
    }

    private void writeFileHandler() throws IOException {
        try (FileWriter fw = new FileWriter(Constants.FILE_PATH)) {
            for (Task task : taskManager.getTasks()) {
                fw.write(task.toFile() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}