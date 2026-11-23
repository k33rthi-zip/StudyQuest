package use_case.complete_task;

/**
 * The input boundary for the Complete Task Use Case.
 */
public interface CompleteTaskInputBoundary {

    /**
     * Executes the complete task use case.
     * @param inputData the input data
     */
    void execute(CompleteTaskInputData inputData);
}