package interface_adapter.add_task;

import java.util.ArrayList;
import java.util.List;
import use_case.add_task.AddTaskOutputBoundary;
import use_case.add_task.AddTaskOutputData;

public class AddTaskPresenter implements AddTaskOutputBoundary {
    private static final int MAX_NOTE_LENGTH = 30;
    private final AddTaskViewModel viewModel;

    public AddTaskPresenter(AddTaskViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(AddTaskOutputData outputData) {
        final List<String> rows = new ArrayList<>();
        for (AddTaskOutputData.TaskSummary task : outputData.getUpcomingTasks()) {
            rows.add(formatRow(task));
        }
        final AddTaskState state = viewModel.getState();
        state.setUpcomingTasks(rows);
        state.setError("");
        state.setTaskAdded(true);
        state.setConfirmPastDueRequested(false);
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddTaskState state = viewModel.getState();
        state.setError(errorMessage);
        state.setTaskAdded(false);
        state.setConfirmPastDueRequested(false);
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareConfirmPastDueView() {
        final AddTaskState state = viewModel.getState();
        state.setError("");
        state.setTaskAdded(false);
        state.setConfirmPastDueRequested(true);
        viewModel.notifyStateChanged();
    }

    private static String formatRow(AddTaskOutputData.TaskSummary task) {
        final StringBuilder row = new StringBuilder();
        row.append('[').append(priorityLabel(task.getPriority())).append("]  ")
                .append(task.getDueDate()).append("  ")
                .append(task.getTitle());
        final String note = task.getNote().replace('\n', ' ').trim();
        if (!note.isEmpty()) {
            final String shortNote = note.length() > MAX_NOTE_LENGTH
                    ? note.substring(0, MAX_NOTE_LENGTH) + "..." : note;
            row.append("  - ").append(shortNote);
        }
        return row.toString();
    }

    private static String priorityLabel(int priority) {
        if (priority == 3) {
            return "HIGH";
        }
        else if (priority == 2) {
            return "MID ";
        }
        return "LOW ";
    }
}