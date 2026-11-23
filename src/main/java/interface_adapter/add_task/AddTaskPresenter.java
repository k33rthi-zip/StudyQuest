package interface_adapter.add_task;

import java.util.ArrayList;
import java.util.List;

import entity.Task;
import interface_adapter.task_list.AddTaskState;
import interface_adapter.task_list.AddTaskViewModel;
import interface_adapter.task_list.TaskRowFormatter;
import use_case.add_task.AddTaskOutputBoundary;
import use_case.add_task.AddTaskOutputData;

/**
 * The Presenter for the Add Task Use Case.
 */
public class AddTaskPresenter implements AddTaskOutputBoundary {

    private final AddTaskViewModel viewModel;

    public AddTaskPresenter(AddTaskViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(AddTaskOutputData outputData) {
        final List<String> rows = new ArrayList<>();
        final List<String> ids = new ArrayList<>();
        for (Task task : outputData.getUpcomingTasks()) {
            rows.add(TaskRowFormatter.format(task));
            ids.add(task.getId());
        }
        final AddTaskState state = viewModel.getState();
        state.setUpcomingTasks(rows);
        state.setUpcomingTaskIds(ids);
        state.setError("");
        state.setSuccessMessage("Task added.");
        state.setTaskAdded(true);
        state.setConfirmPastDueRequested(false);
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddTaskState state = viewModel.getState();
        state.setError(errorMessage);
        state.setSuccessMessage("");
        state.setTaskAdded(false);
        state.setConfirmPastDueRequested(false);
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareConfirmPastDueView() {
        final AddTaskState state = viewModel.getState();
        state.setError("");
        state.setSuccessMessage("");
        state.setTaskAdded(false);
        state.setConfirmPastDueRequested(true);
        viewModel.notifyStateChanged();
    }
}