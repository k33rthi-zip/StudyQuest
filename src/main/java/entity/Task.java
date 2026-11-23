
package entity;

import java.time.LocalDate;

public class Task {
    private final String id;
    private final String title;
    private final LocalDate dueDate;
    private final int priority;   // 1 low, 2 mid, 3 high
    private final String note;
    private boolean completed;

    public Task(String id, String title, LocalDate dueDate, int priority, String note) {
        if (title == "") {
            throw new IllegalArgumentException("Add a title to the task");
        }

        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be missing");
        }

        if (priority < 1 || priority > 3) {
            throw new IllegalArgumentException("Priority must be 1, 2 or 3");
        }
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.note = note == null ? "" : note;
        this.completed = false;

    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public int getPriority() {
        return priority;
    }
    public String getNote() {
        return note;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void markComplete() {
        this.completed = true;
    }
    public boolean isOverdue(LocalDate today) {
        return !completed && dueDate.isBefore(today);
    }
}