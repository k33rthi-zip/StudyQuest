package use_case.complete_task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Works out the coins for completing a task.
 *
 * <p>Base reward is 5 coins per priority level (Low 5, Mid 10, High 15). Finishing early adds
 * "priority" coins for each full day early, counting at most 7 days. Finishing on the due date
 * earns the base reward. An overdue task earns half the base reward, at least 1 coin.
 */
public final class CoinRewardCalculator {

    public static final int COINS_PER_PRIORITY_LEVEL = 5;
    public static final int MAX_EARLY_DAYS_COUNTED = 7;

    private CoinRewardCalculator() {
    }

    /**
     * Days between today and the due date.
     * @param dueDate the due date
     * @param today the day the task is completed
     * @return positive if early, 0 if on the due date, negative if overdue
     */
    public static int daysEarly(LocalDate dueDate, LocalDate today) {
        return (int) ChronoUnit.DAYS.between(today, dueDate);
    }

    /**
     * Calculates the coin reward.
     * @param priority 1 (low), 2 (mid) or 3 (high)
     * @param dueDate the task's due date
     * @param today the day the task is completed
     * @return the coins earned
     */
    public static int calculate(int priority, LocalDate dueDate, LocalDate today) {
        final int base = priority * COINS_PER_PRIORITY_LEVEL;
        final int daysEarly = daysEarly(dueDate, today);

        final int coins;
        if (daysEarly > 0) {
            coins = base + Math.min(daysEarly, MAX_EARLY_DAYS_COUNTED) * priority;
        }
        else if (daysEarly == 0) {
            coins = base;
        }
        else {
            coins = Math.max(1, base / 2);
        }
        return coins;
    }
}