
package entity;

import java.time.Instant;
import java.time.Duration;

public class StudySession {

    private final int plannedMinutes;
    private int minutesCompleted;
    private String status;
    private final Instant startTime;

    public StudySession(int plannedMinutes) {
        if (plannedMinutes <= 0) {
            throw new IllegalArgumentException(
                    "Study duration must be positive."
            );
        }

        this.plannedMinutes = plannedMinutes;
        this.minutesCompleted = 0;
        this.status = "In Progress";
        this.startTime = Instant.now();
    }

    public long getRemainingSeconds() {
        long elapsedSeconds = Duration.between(
                startTime, Instant.now()
        ).getSeconds();

        return Math.max(
                0, plannedMinutes * 60L - elapsedSeconds
        );
    }

    public boolean isFinished() {
        return getRemainingSeconds() == 0;
    }

    public int getPlannedMinutes() {
        return plannedMinutes;
    }

    public int getMinutesCompleted() {
        return minutesCompleted;
    }

    public String getStatus() {
        return status;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void complete() {
        if (!status.equals("In Progress")) {
            throw new IllegalStateException(
                    "Session is not active."
            );
        }

        if (!isFinished()) {
            throw new IllegalStateException(
                    "Session has not finished yet."
            );
        }

        minutesCompleted = plannedMinutes;
        status = "Completed";
    }

    public void quit(int minutesElapsed) {
        if (status.equals("Completed")
                || status.equals("Quit")) {
            return;
        }

        if (minutesElapsed < 0) {
            throw new IllegalArgumentException(
                    "Elapsed minutes cannot be negative."
            );
        }

        minutesCompleted = Math.min(
                minutesElapsed, plannedMinutes
        );

        status = "Quit";
    }

    public int earnCoin() {
        if (status.equals("Completed")) {
            return plannedMinutes / 5;
        }

        return 0;
    }
}
