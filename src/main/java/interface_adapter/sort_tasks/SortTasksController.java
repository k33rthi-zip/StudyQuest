package interface_adapter.sort_tasks;

import use_case.sort_tasks.SortOption;
import use_case.sort_tasks.SortTasksInputBoundary;
import use_case.sort_tasks.SortTasksInputData;

/**
 * The controller for the Sort Tasks Use Case.
 */
public class SortTasksController {

    public static final String PRIORITY = "Priority";
    public static final String DUE_DATE = "Due Date";

    private final SortTasksInputBoundary interactor;

    public SortTasksController(SortTasksInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the sort tasks use case.
     * @param sortBy "Priority" or "Due Date", as shown in the Sort by dropdown
     * @param announceResult true if the student just picked this sort, false if the list is only being
     *                       re-sorted after adding or completing a task
     */
    public void execute(String sortBy, boolean announceResult) {
        final SortOption option = PRIORITY.equals(sortBy) ? SortOption.PRIORITY : SortOption.DUE_DATE;
        interactor.execute(new SortTasksInputData(option, announceResult));
    }
}