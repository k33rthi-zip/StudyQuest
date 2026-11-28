package use_case.complete_task;

/**
 * The output boundary for the Complete Task Use Case.
 */
public interface CompleteTaskOutputBoundary {

    /**
     * Prepares the success view: the task is complete and the coins were awarded.
     * @param outputData the reward details and the refreshed upcoming list
     */
    void prepareSuccessView(CompleteTaskOutputData outputData);

    /**
     * Prepares the failure view.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
