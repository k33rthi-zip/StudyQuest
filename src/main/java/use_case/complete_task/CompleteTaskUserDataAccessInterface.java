package use_case.complete_task;

import java.util.List;

import entity.Task;

/**
 * The data access interface for the Complete Task Use Case.
 */
public interface CompleteTaskUserDataAccessInterface {

    /**
     * Finds a task by id.
     * @param taskId the task id
     * @return the task, or null if there is none
     */
    Task getTaskById(String taskId);

    /**
     * Stores the changed task.
     * @param task the task to update
     */
    void updateTask(Task task);

    /**
     * Returns every stored task, complete or not.
     * @return all tasks
     */
    List<Task> getAllTasks();
}