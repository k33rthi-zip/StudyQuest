package use_case.sort_tasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Task;

/**
 * The Sort Tasks Interactor.
 *
 * <p>By priority: High first, then Mid, then Low, with the earliest due date first inside each level.
 * By due date: earliest due date first and priority does not matter. Ties are broken by title so the
 * order is always the same.
 */
public class SortTasksInteractor implements SortTasksInputBoundary {

    private final SortTasksUserDataAccessInterface dataAccess;
    private final SortTasksOutputBoundary presenter;

    public SortTasksInteractor(SortTasksUserDataAccessInterface dataAccess, SortTasksOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SortTasksInputData inputData) {
        final List<Task> incomplete = new ArrayList<>();
        for (Task task : dataAccess.getAllTasks()) {
            if (!task.isCompleted()) {
                incomplete.add(task);
            }
        }

        final Comparator<Task> byDueDate = Comparator.comparing(Task::getDueDate)
                .thenComparing(Task::getTitle);
        if (inputData.getSortOption() == SortOption.PRIORITY) {
            incomplete.sort(Comparator.comparingInt(Task::getPriority).reversed().thenComparing(byDueDate));
        }
        else {
            incomplete.sort(byDueDate);
        }

        presenter.prepareSuccessView(new SortTasksOutputData(incomplete, inputData.getSortOption(),
                inputData.isAnnounceResult()));
    }
}