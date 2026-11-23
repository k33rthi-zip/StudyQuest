package interface_adapter.task_list;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Task Manager screen. It is shared by the Add Task and Complete Task use cases.
 */
public class AddTaskState {

    private String error = "";
    private String successMessage = "";
    private List<String> upcomingTasks = new ArrayList<>();
    private List<String> upcomingTaskIds = new ArrayList<>();
    private boolean taskAdded;
    private boolean confirmPastDueRequested;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    /**
     * A message for the status bar after something worked, like "Task added." or the coins earned.
     * @return the message, or an empty string
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public List<String> getUpcomingTasks() {
        return upcomingTasks;
    }

    public void setUpcomingTasks(List<String> upcomingTasks) {
        this.upcomingTasks = upcomingTasks;
    }

    /**
     * The task ids, in the same order as {@link #getUpcomingTasks()}, so the view knows which task a row is.
     * @return the ids
     */
    public List<String> getUpcomingTaskIds() {
        return upcomingTaskIds;
    }

    public void setUpcomingTaskIds(List<String> upcomingTaskIds) {
        this.upcomingTaskIds = upcomingTaskIds;
    }

    /**
     * True right after a task was added, so the view knows to clear its form.
     * @return whether the last attempt added a task
     */
    public boolean isTaskAdded() {
        return taskAdded;
    }

    public void setTaskAdded(boolean taskAdded) {
        this.taskAdded = taskAdded;
    }

    /**
     * True when the view should ask "due date is in the past, add anyway?".
     * @return whether a confirmation is needed
     */
    public boolean isConfirmPastDueRequested() {
        return confirmPastDueRequested;
    }

    public void setConfirmPastDueRequested(boolean confirmPastDueRequested) {
        this.confirmPastDueRequested = confirmPastDueRequested;
    }
}