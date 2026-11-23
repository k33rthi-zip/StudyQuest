package use_case.select_study_duration;

public class SelectDurationInputData {
    private final int selectedMinutes;

    public SelectDurationInputData(int selectedMinutes) {
        this.selectedMinutes = selectedMinutes;
    }

    public int getSelectedMinutes() {
        return selectedMinutes;
    }
}
