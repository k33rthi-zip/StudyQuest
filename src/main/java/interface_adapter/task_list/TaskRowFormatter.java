package interface_adapter.task_list;

import entity.Task;

/**
 * Turns a task into the text for one row of the Task List table. The columns are joined with
 * {@link #SEPARATOR}, in the order given by {@link #COLUMN_NAMES}, e.g. "High", "2026-12-31", "Essay", "draft intro".
 * Shared by the Add Task, Complete Task and Sort Tasks presenters.
 */
public final class TaskRowFormatter {

    public static final String SEPARATOR = "\t";
    public static final String[] COLUMN_NAMES = {"Priority", "Due Date", "Title", "Notes"};

    private TaskRowFormatter() {
    }

    public static String format(Task task) {
        return priorityLabel(task.getPriority())
                + SEPARATOR + task.getDueDate()
                + SEPARATOR + clean(task.getTitle())
                + SEPARATOR + clean(task.getNote());
    }

    private static String clean(String text) {
        return text.replace('\t', ' ').replace('\n', ' ').trim();
    }

    private static String priorityLabel(int priority) {
        final String label;
        if (priority == 3) {
            label = "High";
        }
        else if (priority == 2) {
            label = "Mid";
        }
        else {
            label = "Low";
        }
        return label;
    }
}