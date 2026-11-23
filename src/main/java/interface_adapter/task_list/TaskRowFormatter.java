package interface_adapter.task_list;

import entity.Task;

/**
 * Turns a task into the one-line text shown in the Task List, e.g. "[HIGH]  2026-12-31  Essay  - draft intro".
 * Shared by the Add Task and Complete Task presenters.
 */
public final class TaskRowFormatter {

    private static final int MAX_NOTE_LENGTH = 30;

    private TaskRowFormatter() {
    }

    public static String format(Task task) {
        final StringBuilder row = new StringBuilder();
        row.append('[').append(priorityLabel(task.getPriority())).append("]  ")
                .append(task.getDueDate()).append("  ")
                .append(task.getTitle());
        final String note = task.getNote().replace('\n', ' ').trim();
        if (!note.isEmpty()) {
            final String shortNote = note.length() > MAX_NOTE_LENGTH
                    ? note.substring(0, MAX_NOTE_LENGTH) + "..." : note;
            row.append("  - ").append(shortNote);
        }
        return row.toString();
    }

    private static String priorityLabel(int priority) {
        final String label;
        if (priority == 3) {
            label = "HIGH";
        }
        else if (priority == 2) {
            label = "MID";
        }
        else {
            label = "LOW ";
        }
        return label;
    }
}