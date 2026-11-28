
package use_case.quit_study_session;

public class QuitStudySessionOutputData {

    private final int plannedMinutes;
    private final int minutesCompleted;

    public QuitStudySessionOutputData(
            int plannedMinutes,
            int minutesCompleted) {

        this.plannedMinutes = plannedMinutes;
        this.minutesCompleted = minutesCompleted;
    }

    public int getPlannedMinutes() {
        return plannedMinutes;
    }

    public int getMinutesCompleted() {
        return minutesCompleted;
    }
}
