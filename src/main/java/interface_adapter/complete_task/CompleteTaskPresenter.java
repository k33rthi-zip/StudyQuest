package interface_adapter.complete_task;

import java.util.ArrayList;
import java.util.List;

import entity.Task;
import interface_adapter.task_list.AddTaskState;
import interface_adapter.task_list.AddTaskViewModel;
import interface_adapter.task_list.TaskRowFormatter;
import use_case.complete_task.CompleteTaskOutputBoundary;
import use_case.complete_task.CompleteTaskOutputData;

/**
 * The Presenter for the Complete Task Use Case. It updates the Task Manager screen's view model,
 * which is shared with the Add Task use case.
 */
public class CompleteTaskPresenter implements CompleteTaskOutputBoundary {

    private final AddTaskViewModel viewModel;

    public CompleteTaskPresenter(AddTaskViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CompleteTaskOutputData outputData) {
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
        state.setSuccessMessage(buildMessage(outputData));
        state.setTaskAdded(false);
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

    private static String buildMessage(CompleteTaskOutputData data) {
        final int daysEarly = data.getDaysEarly();
        final String timing;
        if (daysEarly > 0) {
            timing = daysEarly + (daysEarly == 1 ? " day early" : " days early");
        }
        else if (daysEarly == 0) {
            timing = "on time";
        }
        else {
            timing = "late, reduced reward";
        }
        return "Task completed! +" + data.getCoinsEarned() + " coins (" + timing + "). "
                + "Total: " + data.getTotalCoins() + " coins.";
    }
}