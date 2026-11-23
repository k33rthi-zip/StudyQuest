package interface_adapter.view_account;

import java.util.ArrayList;
import java.util.List;

public class ViewAccountViewModel {

    public static final String VIEW_NAME = "view_account";

    private ViewAccountState state = new ViewAccountState();

    private final List<ViewAccountViewModelListener> listeners =
            new ArrayList<>();

    public ViewAccountState getState() {
        return state;
    }

    public void setState(ViewAccountState state) {
        this.state = state;
    }

    public void addListener(ViewAccountViewModelListener listener) {
        listeners.add(listener);
    }

    public void firePropertyChanged() {
        for (ViewAccountViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
