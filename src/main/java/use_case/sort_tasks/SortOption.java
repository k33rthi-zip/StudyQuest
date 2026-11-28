package use_case.sort_tasks;

/**
 * The ways the student can sort the Task List.
 */
public enum SortOption {
    /** Earliest due date first. Priority is ignored. */
    DUE_DATE,
    /** High priority first, then earliest due date within each priority. */
    PRIORITY
}