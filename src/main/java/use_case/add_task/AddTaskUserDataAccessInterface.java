
package use_case.add_task;

import java.util.List;
import entity.Task;

public interface AddTaskUserDataAccessInterface {
    void saveTask(Task task);
    List<Task> getAllTasks();
}