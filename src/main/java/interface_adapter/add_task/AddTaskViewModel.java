package interface_adapter.add_task;

import java.util.ArrayList;
import java.util.List;

public class AddTaskViewModel {
    public static final String VIEW_NAME = "add task";

    private final List<AddTaskViewModelListener> listeners = new ArrayList<>();
    private AddTaskState state = new AddTaskState();

    public AddTaskState getState() { return state; }
    public void setState(AddTaskState state) { this.state = state; }
    public void addListener(AddTaskViewModelListener listener) { listeners.add(listener); }

    public void notifyStateChanged() {
        for (AddTaskViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}