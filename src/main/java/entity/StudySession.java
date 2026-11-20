package entity;


public class StudySession {

    private final int plannedMinutes;
    private int minutesCompleted;
    private String status;

    public StudySession(int plannedMinutes) {
        if (plannedMinutes <= 0) {
            throw new IllegalArgumentException(
                    "Study duration must be positive."
            );
        }

        this.plannedMinutes = plannedMinutes;
        this.minutesCompleted = 0;
        this.status = "In Progress";
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

    public void pause() {
        if (status.equals("In Progress")) {
            status = "Paused";
        }
    }

    public void resume() {
        if (status.equals("Paused")) {
            status = "In Progress";
        }
    }

    public void complete() {
        if (status.equals("In Progress")
                && minutesCompleted >= plannedMinutes) {
            status = "Completed";
        }
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
