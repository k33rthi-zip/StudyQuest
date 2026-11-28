
package use_case.complete_study_session;

public class CompleteStudySessionOutputData {

    private final int minutesCompleted;
    private final int coinsEarned;
    private final int totalCoins;

    public CompleteStudySessionOutputData(
            int minutesCompleted,
            int coinsEarned,
            int totalCoins) {

        this.minutesCompleted = minutesCompleted;
        this.coinsEarned = coinsEarned;
        this.totalCoins = totalCoins;
    }

    public int getMinutesCompleted() {
        return minutesCompleted;
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public int getTotalCoins() {
        return totalCoins;
    }
}
