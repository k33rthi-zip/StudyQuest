package data_access;

import java.util.ArrayList;
import java.util.List;
import entity.Task;
import use_case.add_task.AddTaskUserDataAccessInterface;

public class InMemoryTaskDataAccessObject implements AddTaskUserDataAccessInterface {
    private final List<Task> tasks = new ArrayList<>();

    @Override
    public void saveTask(Task task) { tasks.add(task); }

    @Override
    public List<Task> getAllTasks() { return new ArrayList<>(tasks); }
}