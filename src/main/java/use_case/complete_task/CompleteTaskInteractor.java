package use_case.complete_task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entity.Task;

/**
 * The Complete Task Interactor.
 */
public class CompleteTaskInteractor implements CompleteTaskInputBoundary {

    private final CompleteTaskUserDataAccessInterface dataAccess;
    private final CompleteTaskCharacterDataAccessInterface characterDataAccess;
    private final CompleteTaskOutputBoundary presenter;

    public CompleteTaskInteractor(CompleteTaskUserDataAccessInterface dataAccess,
                                  CompleteTaskCharacterDataAccessInterface characterDataAccess,
                                  CompleteTaskOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.characterDataAccess = characterDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(CompleteTaskInputData inputData) {
        final Task task = dataAccess.getTaskById(inputData.getTaskId());
        if (task == null) {
            presenter.prepareFailView("That task no longer exists.");
            return;
        }
        if (task.isCompleted()) {
            presenter.prepareFailView("That task is already complete.");
            return;
        }

        final LocalDate today = LocalDate.now();
        final int coins = CoinRewardCalculator.calculate(task.getPriority(), task.getDueDate(), today);
        final int daysEarly = CoinRewardCalculator.daysEarly(task.getDueDate(), today);

        task.markComplete();
        dataAccess.updateTask(task);
        characterDataAccess.addCoins(coins);

        presenter.prepareSuccessView(new CompleteTaskOutputData(task.getTitle(), coins,
                characterDataAccess.getCoins(), daysEarly, upcomingTasks()));
    }

    private List<Task> upcomingTasks() {
        final List<Task> upcoming = new ArrayList<>();
        for (Task task : dataAccess.getAllTasks()) {
            if (!task.isCompleted()) {
                upcoming.add(task);
            }
        }
        upcoming.sort(Comparator.comparing(Task::getDueDate)
                .thenComparing(Comparator.comparingInt(Task::getPriority).reversed())
                .thenComparing(Task::getTitle));
        return upcoming;
    }
}