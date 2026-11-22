package interface_adapter.add_task;

import java.util.ArrayList;
import java.util.List;

public class AddTaskState {
    private String error = "";
    private List<String> upcomingTasks = new ArrayList<>();
    private boolean taskAdded;
    private boolean confirmPastDueRequested;

    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
    public List<String> getUpcomingTasks() {
        return upcomingTasks;
    }
    public void setUpcomingTasks(List<String> upcomingTasks) {
        this.upcomingTasks = upcomingTasks;
    }
    public boolean isTaskAdded() {
        return taskAdded;
    }
    public void setTaskAdded(boolean taskAdded) {
        this.taskAdded = taskAdded;
    }
    public boolean isConfirmPastDueRequested() {
        return confirmPastDueRequested;
    }
    public void setConfirmPastDueRequested(boolean v) {
        this.confirmPastDueRequested = v;
    }
}