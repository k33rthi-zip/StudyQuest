package use_case.sort_tasks;

/**
 * The input boundary for the Sort Tasks Use Case.
 */
public interface SortTasksInputBoundary {

    /**
     * Executes the sort tasks use case.
     * @param inputData the input data
     */
    void execute(SortTasksInputData inputData);
}