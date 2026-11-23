package use_case.view_account;

public class ViewAccountOutputData {
    private final String username;
    private final int coins;

    public ViewAccountOutputData(String username, int coins) {
        this.username = username;
        this.coins = coins;
    }

    public String getUsername() {
        return username;
    }

    public int getCoins() {
        return coins;
    }
}
