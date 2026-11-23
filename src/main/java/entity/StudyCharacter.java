package entity;

public class StudyCharacter {
    private int coins;
    private final Wardrobe wardrobe;

    public StudyCharacter(int coins, Wardrobe wardrobe) {
        if (coins < 0) {
            throw new IllegalArgumentException("Coins cannot be negative.");
        }

        if (wardrobe == null) {
            throw new IllegalArgumentException("Wardrobe cannot be null.");
        }

        this.coins = coins;
        this.wardrobe = wardrobe;
    }

    public int getCoins() {
        return coins;
    }

    public Wardrobe getWardrobe() {
        return wardrobe;
    }


    public void addCoins(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        coins = Math.addExact(coins, amount);
    }

    public boolean spendCoins(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        if (coins < amount) {
            return false;
        }

        coins -= amount;
        return true;
    }

    public boolean canAfford(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        return coins >= price;
    }



}
