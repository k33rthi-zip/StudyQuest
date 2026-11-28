package use_case.complete_task;

import java.util.List;

import entity.Task;

/**
 * The output data for the Complete Task Use Case.
 */
public class CompleteTaskOutputData {

    private final String taskTitle;
    private final int coinsEarned;
    private final int totalCoins;
    private final int daysEarly;
    private final List<Task> upcomingTasks;

    /**
     * Creates the output data.
     * @param taskTitle the title of the completed task
     * @param coinsEarned the coins awarded for this task
     * @param totalCoins the character's coins after the award
     * @param daysEarly days before the due date it was finished (0 = on time, negative = overdue)
     * @param upcomingTasks the refreshed upcoming list
     */
    public CompleteTaskOutputData(String taskTitle, int coinsEarned, int totalCoins,
                                  int daysEarly, List<Task> upcomingTasks) {
        this.taskTitle = taskTitle;
        this.coinsEarned = coinsEarned;
        this.totalCoins = totalCoins;
        this.daysEarly = daysEarly;
        this.upcomingTasks = upcomingTasks;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public int getDaysEarly() {
        return daysEarly;
    }

    public List<Task> getUpcomingTasks() {
        return upcomingTasks;
    }
}