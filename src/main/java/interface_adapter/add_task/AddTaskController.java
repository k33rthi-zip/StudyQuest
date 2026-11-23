package interface_adapter.add_task;

import use_case.add_task.AddTaskInputBoundary;
import use_case.add_task.AddTaskInputData;

public class AddTaskController {
    private final AddTaskInputBoundary interactor;

    public AddTaskController(AddTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String title, String dueDate, int priority, String note, boolean confirmPastDue) {
        interactor.execute(new AddTaskInputData(title, dueDate, priority, note, confirmPastDue));
    }
}