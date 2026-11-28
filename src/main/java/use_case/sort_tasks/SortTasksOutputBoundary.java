package use_case.sort_tasks;

/**
 * The output boundary for the Sort Tasks Use Case.
 */
public interface SortTasksOutputBoundary {

    /**
     * Prepares the success view: the incomplete tasks in the chosen order.
     * @param outputData the sorted tasks and the sort that was used
     */
    void prepareSuccessView(SortTasksOutputData outputData);
}