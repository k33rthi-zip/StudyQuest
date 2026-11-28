package data_access;

import entity.StudyCharacter;
import use_case.complete_task.CompleteTaskCharacterDataAccessInterface;

/**
 * In-memory data access for the student's character (its coins).
 */
public class InMemoryCharacterDataAccessObject implements CompleteTaskCharacterDataAccessInterface {

    private final StudyCharacter character;

    public InMemoryCharacterDataAccessObject(StudyCharacter character) {
        this.character = character;
    }

    @Override
    public void addCoins(int amount) {
        character.addCoins(amount);
    }

    @Override
    public int getCoins() {
        return character.getCoins();
    }
}