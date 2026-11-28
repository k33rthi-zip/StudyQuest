
package use_case.start_study_session;

public class StartStudySessionInputData {

    private final String username;
    private final int plannedMinutes;

    public StartStudySessionInputData(
            String username, int plannedMinutes) {
        this.username = username;
        this.plannedMinutes = plannedMinutes;
    }

    public String getUsername() {
        return username;
    }

    public int getPlannedMinutes() {
        return plannedMinutes;
    }
}
