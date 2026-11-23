package use_case.add_task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import entity.Task;

/**
 * The Add Task Interactor.
 */
public class AddTaskInteractor implements AddTaskInputBoundary {

    private final AddTaskUserDataAccessInterface dataAccess;
    private final AddTaskOutputBoundary presenter;

    public AddTaskInteractor(AddTaskUserDataAccessInterface dataAccess, AddTaskOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddTaskInputData inputData) {
        final String title = inputData.getTitle() == null ? "" : inputData.getTitle().trim();
        if (title.isEmpty()) {
            presenter.prepareFailView("Please enter a title.");
            return;
        }

        final LocalDate dueDate;
        try {
            dueDate = LocalDate.parse(inputData.getDueDate() == null ? "" : inputData.getDueDate().trim());
        }
        catch (DateTimeParseException ex) {
            presenter.prepareFailView("Due date must look like 2026-12-31.");
            return;
        }

        if (dueDate.isBefore(LocalDate.now()) && !inputData.isConfirmPastDue()) {
            presenter.prepareConfirmPastDueView();
            return;
        }

        final Task task = new Task(UUID.randomUUID().toString(), title, dueDate,
                inputData.getPriority(), inputData.getNote());
        dataAccess.saveTask(task);
        presenter.prepareSuccessView(new AddTaskOutputData(upcomingTasks()));
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