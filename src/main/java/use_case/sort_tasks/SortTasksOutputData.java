package use_case.sort_tasks;

import java.util.List;

import entity.Task;

/**
 * The output data for the Sort Tasks Use Case.
 */
public class SortTasksOutputData {

    private final List<Task> sortedTasks;
    private final SortOption sortOption;
    private final boolean announceResult;

    public SortTasksOutputData(List<Task> sortedTasks, SortOption sortOption, boolean announceResult) {
        this.sortedTasks = sortedTasks;
        this.sortOption = sortOption;
        this.announceResult = announceResult;
    }

    public List<Task> getSortedTasks() {
        return sortedTasks;
    }

    public SortOption getSortOption() {
        return sortOption;
    }

    public boolean isAnnounceResult() {
        return announceResult;
    }
}