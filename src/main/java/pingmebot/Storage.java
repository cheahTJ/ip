package pingmebot;

import pingmebot.task.Task;
import pingmebot.task.ToDos;
import pingmebot.task.Deadline;
import pingmebot.task.Events;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Scanner;

<<<<<<< HEAD:src/main/java/pingmebot/fileStorage.java
/**
 * Helps to load tasks from and updating tasks to local file.
 * It provides methods to boot tasks, if any, from and write any new tasks into the local file.
 */
public class fileStorage {
    protected File myFile;
    protected String filePath;

    /**
     * Creates a filestorage object with a specified file path.
     *
     * @param filePath The file path to the local file.
     * @throws myBotException If the path to the file does not exist.
     */
    public fileStorage(String filePath) throws myBotException {
=======
public class Storage {
    protected File myFile;
    protected String filePath;
    public Storage(String filePath) throws PingMeException {
>>>>>>> branch-A-CodingStandard:src/main/java/pingmebot/Storage.java
        this.myFile = new File(filePath);
        this.filePath = filePath;
        if (!myFile.exists()) {
            try {
                if (this.myFile.createNewFile()) {
                    System.out.println("File has been successfully created");
                } else {
                    System.out.println("Error creating file...");
                }
            } catch (IOException e) {
                throw new PingMeException(e.getMessage());
            }
        }
    }

<<<<<<< HEAD:src/main/java/pingmebot/fileStorage.java
    /**
     * Loads tasks into list of tasks which is then used to create the tasklist.
     *
     * @return List of tasks, if any, from the local file.
     * @throws myBotException If the path to the file is not found or when syntax of tasks stored in the localfile is not understood.
     */
    public ArrayList<Task> bootingUp() throws myBotException {
=======
    public ArrayList<Task> bootingUp() throws PingMeException {
>>>>>>> branch-A-CodingStandard:src/main/java/pingmebot/Storage.java
        ArrayList<Task> tasks = new ArrayList<>();
        if (this.myFile.length() == 0) {
            tasks = new ArrayList<>();
        } else {
            try {
                Scanner sc = new Scanner(this.myFile);
                while (sc.hasNextLine()) {
                    String text = sc.nextLine();
                    String[] segmentedText = text.split("\\|");

                    if (segmentedText[0].trim().equals("todo")) {
                        int isTaskCompleted = Integer.parseInt(segmentedText[1].trim());
                        String description = segmentedText[2].trim();
                        ToDos todo = new ToDos(description);
                        if (isTaskCompleted == 1) {
                            todo.markAsDone();
                        }
                        tasks.add(todo);

                    } else if (segmentedText[0].trim().equals("deadline")) {
                        int isTaskCompleted = Integer.parseInt(segmentedText[1].trim());
                        String description = segmentedText[2].trim();
                        String deadlineTime = segmentedText[3].trim();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
                        LocalDateTime parsedDateTime = LocalDateTime.parse(deadlineTime, formatter);
                        Deadline deadline = new Deadline(description, parsedDateTime);
                        if (isTaskCompleted == 1) {
                            deadline.markAsDone();
                        }
                        tasks.add(deadline);

                    } else if (segmentedText[0].trim().equals("event")) {
                        int isTaskCompleted = Integer.parseInt(segmentedText[1].trim());
                        String description = segmentedText[2].trim();
                        String from = " " + segmentedText[3].trim();
                        String to = " " + segmentedText[4].trim();
                        Events event = new Events(description,from,to);
                        if (isTaskCompleted == 1) {
                            event.markAsDone();
                        }
                        tasks.add(event);

                    } else {
                        throw new PingMeException("Weird expression found!");
                    }
                }

            } catch (FileNotFoundException e) {
                throw new PingMeException(e.getMessage());
            }
        }
        return tasks;
    }

<<<<<<< HEAD:src/main/java/pingmebot/fileStorage.java
    /**
     * Updates and writes all the tasks in the tasklist into the local file.
     *
     * @param tasks The list of all tasks.
     * @throws myBotException If the path to the file is not found.
     */
    public void updateFile(ArrayList<Task> tasks) throws myBotException {
=======
    public void updateFile(ArrayList<Task> tasks) throws PingMeException {
>>>>>>> branch-A-CodingStandard:src/main/java/pingmebot/Storage.java
        try {
            FileWriter fw = new FileWriter(this.filePath);
            for (Task t : tasks) {
                int isCompleted = t.hasCompleted();
                if (t instanceof ToDos) {
                    String toWrite = ((ToDos) t).updateToDoText(isCompleted);
                    fw.write(toWrite + System.lineSeparator());

                } else if (t instanceof Deadline) {
                    String toWrite = ((Deadline) t).updateDeadlineText(isCompleted);
                    fw.write(toWrite + System.lineSeparator());

                } else if (t instanceof Events) {
                    String toWrite = ((Events) t).updateEventText(isCompleted);
                    fw.write(toWrite + System.lineSeparator());
                }
            }
            fw.close();

        } catch (IOException e) {
<<<<<<< HEAD:src/main/java/pingmebot/fileStorage.java
            throw new myBotException("There is no file to be updated!");
=======
            throw new PingMeException("There is not file to be updated!");
>>>>>>> branch-A-CodingStandard:src/main/java/pingmebot/Storage.java
        }
    }
}


