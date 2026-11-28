
package interface_adapter.study_mode;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StudyModeViewModel {

    private StudyModeState state = new StudyModeState();
    public static final String VIEW_NAME = "study mode";

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public StudyModeState getState() {
        return state;
    }

    public void setState(StudyModeState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
