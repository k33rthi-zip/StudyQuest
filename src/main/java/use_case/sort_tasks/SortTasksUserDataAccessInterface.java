package use_case.sort_tasks;

import java.util.List;

import entity.Task;

/**
 * The data access interface for the Sort Tasks Use Case.
 */
public interface SortTasksUserDataAccessInterface {

    /**
     * Returns every stored task, complete or not.
     * @return all tasks
     */
    List<Task> getAllTasks();
}