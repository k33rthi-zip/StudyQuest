package interface_adapter.complete_task;

import use_case.complete_task.CompleteTaskInputBoundary;
import use_case.complete_task.CompleteTaskInputData;

/**
 * The controller for the Complete Task Use Case.
 */
public class CompleteTaskController {

    private final CompleteTaskInputBoundary interactor;

    public CompleteTaskController(CompleteTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the complete task use case.
     * @param taskId the id of the task the student selected
     */
    public void execute(String taskId) {
        interactor.execute(new CompleteTaskInputData(taskId));
    }
}