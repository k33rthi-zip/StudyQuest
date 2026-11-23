package use_case.complete_task;

/**
 * The input data for the Complete Task Use Case.
 */
public class CompleteTaskInputData {

    private final String taskId;

    public CompleteTaskInputData(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}