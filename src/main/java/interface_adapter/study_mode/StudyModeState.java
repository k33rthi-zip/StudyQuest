
package interface_adapter.study_mode;

public class StudyModeState {

    private int selectedMinutes = 25;
    private String error = null;

    public int getSelectedMinutes() {
        return selectedMinutes;
    }

    public void setSelectedMinutes(int selectedMinutes) {
        this.selectedMinutes = selectedMinutes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
