package use_case.select_study_duration;

public class SelectDurationOutputData
{
    private final int selectedMinutes;

    public SelectDurationOutputData(int selectedMinutes) {
        this.selectedMinutes = selectedMinutes;
    }

    public int getSelectedMinutes() {
        return selectedMinutes;
    }
}
