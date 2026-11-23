package use_case.complete_task;

/**
 * The data access interface for the character's coins, used by the Complete Task Use Case.
 */
public interface CompleteTaskCharacterDataAccessInterface {

    /**
     * Adds coins to the student's character.
     * @param amount the coins to add
     */
    void addCoins(int amount);

    /**
     * Returns the character's current coins.
     * @return the coin total
     */
    int getCoins();
}