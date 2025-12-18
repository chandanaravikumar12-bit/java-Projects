import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;   // for FileReader, FileWriter, IOException

// Represents one task in the list
class Task {
    private int id;
    private String title;
    private String description;
    private boolean completed;

    public Task(int id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // convenience constructor for new tasks (not completed yet)
    public Task(int id, String title, String description) {
        this(id, title, description, false);
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return id + ". " + title + " - " + description +
               " [" + (completed ? "Done" : "Pending") + "]";
    }

    // convert task to one line for file: id|title|description|completed
    public String toFileString() {
        return id + "|" + title + "|" + description + "|" + completed;
    }

    // create Task from one line in file
    public static Task fromFileString(String line) {
        String[] parts = line.split("\\|", 4);
        if (parts.length < 4) return null;
        int id = Integer.parseInt(parts[0]);
        String title = parts[1];
        String desc = parts[2];
        boolean completed = Boolean.parseBoolean(parts[3]);
        return new Task(id, title, desc, completed);
    }
}

public class TaskApp1 {
    private static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        // 1. Load tasks from file on start
        int nextId = loadTasksFromFile(tasks);

        while (true) {
            System.out.println("\n=== TASK MANAGER (CRUD + FILE I/O) ===");
            System.out.println("1. Create task");
            System.out.println("2. View tasks");
            System.out.println("3. Update task");
            System.out.println("4. Delete task");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear leftover newline

            switch (choice) {
                case 1: // CREATE
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();

                    Task t = new Task(nextId++, title, desc);
                    tasks.add(t);
                    System.out.println("Task created.");

                    // save to file
                    saveTasksToFile(tasks);
                    break;

                case 2: // READ
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks to show.");
                    } else {
                        System.out.println("\n--- Task List ---");
                        for (Task task : tasks) {
                            System.out.println(task);
                        }
                    }
                    break;

                case 3: // UPDATE
                    System.out.print("Enter task id to update: ");
                    int uid = sc.nextInt();
                    sc.nextLine(); // clear newline
                    Task ut = findById(tasks, uid);

                    if (ut == null) {
                        System.out.println("Task not found.");
                    } else {
                        System.out.print("New title (leave empty to keep same): ");
                        String newTitle = sc.nextLine();
                        if (!newTitle.isEmpty()) {
                            ut.setTitle(newTitle);
                        }

                        System.out.print("New description (leave empty to keep same): ");
                        String newDesc = sc.nextLine();
                        if (!newDesc.isEmpty()) {
                            ut.setDescription(newDesc);
                        }

                        System.out.print("Is completed? (y/n, leave empty to keep same): ");
                        String ans = sc.nextLine();
                        if (ans.equalsIgnoreCase("y")) {
                            ut.setCompleted(true);
                        } else if (ans.equalsIgnoreCase("n")) {
                            ut.setCompleted(false);
                        }

                        System.out.println("Task updated.");

                        // save to file
                        saveTasksToFile(tasks);
                    }
                    break;

                case 4: // DELETE
                    System.out.print("Enter task id to delete: ");
                    int did = sc.nextInt();
                    sc.nextLine();
                    Task dt = findById(tasks, did);
                    if (dt == null) {
                        System.out.println("Task not found.");
                    } else {
                        tasks.remove(dt);
                        System.out.println("Task deleted.");

                        // save to file
                        saveTasksToFile(tasks);
                    }
                    break;

                case 5: // EXIT
                    System.out.println("Exiting application...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // helper: find task by id in the list
    private static Task findById(ArrayList<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    // read tasks from file, return nextId
    private static int loadTasksFromFile(ArrayList<Task> tasks) {
        int maxId = 0;
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            // no file yet -> nothing to load
            return 1;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task t = Task.fromFileString(line);
                if (t != null) {
                    tasks.add(t);
                    if (t.getId() > maxId) {
                        maxId = t.getId();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return maxId + 1; // next id after the largest one found
    }

    // write all tasks to file
    private static void saveTasksToFile(ArrayList<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task t : tasks) {
                bw.write(t.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
