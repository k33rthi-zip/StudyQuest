package interface_adapter.sort_tasks;

import java.util.ArrayList;
import java.util.List;

import entity.Task;
import interface_adapter.task_list.AddTaskState;
import interface_adapter.task_list.AddTaskViewModel;
import interface_adapter.task_list.TaskRowFormatter;
import use_case.sort_tasks.SortOption;
import use_case.sort_tasks.SortTasksOutputBoundary;
import use_case.sort_tasks.SortTasksOutputData;

/**
 * The Presenter for the Sort Tasks Use Case. It updates the Task Manager screen's view model,
 * which is shared with the Add Task and Complete Task use cases.
 */
public class SortTasksPresenter implements SortTasksOutputBoundary {

    private final AddTaskViewModel viewModel;

    public SortTasksPresenter(AddTaskViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SortTasksOutputData outputData) {
        final List<String> rows = new ArrayList<>();
        final List<String> ids = new ArrayList<>();
        for (Task task : outputData.getSortedTasks()) {
            rows.add(TaskRowFormatter.format(task));
            ids.add(task.getId());
        }
        final AddTaskState state = viewModel.getState();
        state.setUpcomingTasks(rows);
        state.setUpcomingTaskIds(ids);
        state.setConfirmPastDueRequested(false);

        // When the list is only being re-sorted after adding or completing a task, leave the status
        // message alone so "Task added." or the coins earned stay visible.
        if (outputData.isAnnounceResult()) {
            state.setError("");
            state.setTaskAdded(false);
            if (rows.isEmpty()) {
                state.setSuccessMessage("");
            }
            else {
                state.setSuccessMessage("Sorted by " + label(outputData.getSortOption()) + ".");
            }
        }
        viewModel.notifyStateChanged();
    }

    private static String label(SortOption option) {
        return option == SortOption.PRIORITY ? "Priority" : "Due Date";
    }
}