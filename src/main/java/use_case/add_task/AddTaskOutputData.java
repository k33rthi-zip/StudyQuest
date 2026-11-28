package use_case.add_task;

import java.util.List;

import entity.Task;

/**
 * The output data for the Add Task Use Case: the upcoming tasks list, ordered by due date then priority.
 */
public class AddTaskOutputData {

    private final List<Task> upcomingTasks;

    public AddTaskOutputData(List<Task> upcomingTasks) {
        this.upcomingTasks = upcomingTasks;
    }

    public List<Task> getUpcomingTasks() {
        return upcomingTasks;
    }
}