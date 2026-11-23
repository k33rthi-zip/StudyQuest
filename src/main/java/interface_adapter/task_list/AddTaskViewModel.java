package interface_adapter.task_list;

import java.util.ArrayList;
import java.util.List;

/**
 * The View Model for the Task Manager screen. It is shared by the Add Task and Complete Task use cases:
 * each presenter updates it and AddTaskView listens to it.
 */
public class AddTaskViewModel {

    public static final String VIEW_NAME = "add task";

    private final List<AddTaskViewModelListener> listeners = new ArrayList<>();

    private AddTaskState state = new AddTaskState();

    public AddTaskState getState() {
        return state;
    }

    public void setState(AddTaskState state) {
        this.state = state;
    }

    public void addListener(AddTaskViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (AddTaskViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}