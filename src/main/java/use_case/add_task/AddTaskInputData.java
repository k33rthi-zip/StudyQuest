

package use_case.add_task;

public class AddTaskInputData {

    private final String title;
    private final String dueDate;        // raw text, YYYY-MM-DD
    private final int priority;
    private final String note;
    private final boolean confirmPastDue;

    public AddTaskInputData(String title, String dueDate, int priority, String note, boolean confirmPastDue) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.note = note;
        this.confirmPastDue = confirmPastDue;
    }

    public String getTitle() {

        return title;
    }
    public String getDueDate() {
        return dueDate;
    }
    public int getPriority() {
        return priority;
    }
    public String getNote() {
        return note;
    }
    public boolean isConfirmPastDue() {
        return confirmPastDue;
    }
}