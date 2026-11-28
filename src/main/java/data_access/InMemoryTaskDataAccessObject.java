package data_access;

import java.util.ArrayList;
import java.util.List;

import entity.Task;
import use_case.add_task.AddTaskUserDataAccessInterface;
import use_case.complete_task.CompleteTaskUserDataAccessInterface;

/**
 * In-memory data access for tasks. Tasks are lost when the app closes.
 */
public class InMemoryTaskDataAccessObject
        implements AddTaskUserDataAccessInterface, CompleteTaskUserDataAccessInterface {

    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void saveTask(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Task getTaskById(String taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public void updateTask(Task task) {
        // Tasks are held by reference, so the stored object is already up to date.
    }
}