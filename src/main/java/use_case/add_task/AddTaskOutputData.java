
package use_case.add_task;

import java.time.LocalDate;
import java.util.List;

public class AddTaskOutputData {
    private final List<TaskSummary> upcomingTasks;

    public AddTaskOutputData(List<TaskSummary> upcomingTasks) {
        this.upcomingTasks = upcomingTasks;
    }

    public List<TaskSummary> getUpcomingTasks() { return upcomingTasks; }

    public static final class TaskSummary {
        private final String title;
        private final LocalDate dueDate;
        private final int priority;
        private final String note;

        public TaskSummary(String title, LocalDate dueDate, int priority, String note) {
            this.title = title;
            this.dueDate = dueDate;
            this.priority = priority;
            this.note = note;
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
    }
}