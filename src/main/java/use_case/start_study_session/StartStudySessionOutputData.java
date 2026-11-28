
package use_case.start_study_session;

import java.time.Instant;

public class StartStudySessionOutputData {

    private final int plannedMinutes;
    private final long remainingSeconds;
    private final Instant endTime;

    public StartStudySessionOutputData(
            int plannedMinutes,
            long remainingSeconds,
            Instant endTime) {

        this.plannedMinutes = plannedMinutes;
        this.remainingSeconds = remainingSeconds;
        this.endTime = endTime;
    }

    public int getPlannedMinutes() {
        return plannedMinutes;
    }

    public long getRemainingSeconds() {
        return remainingSeconds;
    }

    public Instant getEndTime() {
        return endTime;
    }
}
