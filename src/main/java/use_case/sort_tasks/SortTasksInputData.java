package use_case.sort_tasks;

/**
 * The input data for the Sort Tasks Use Case.
 */
public class SortTasksInputData {

    private final SortOption sortOption;
    private final boolean announceResult;

    /**
     * Creates the input data.
     * @param sortOption how to sort the tasks
     * @param announceResult true if the student asked for this sort and should be told about it,
     *                       false if the list is just being re-sorted after another action
     */
    public SortTasksInputData(SortOption sortOption, boolean announceResult) {
        this.sortOption = sortOption;
        this.announceResult = announceResult;
    }

    public SortOption getSortOption() {
        return sortOption;
    }

    public boolean isAnnounceResult() {
        return announceResult;
    }
}