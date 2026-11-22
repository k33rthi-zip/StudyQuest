
package use_case.add_task;

public interface AddTaskOutputBoundary {
    void prepareSuccessView(AddTaskOutputData outputData);
    void prepareFailView(String errorMessage);
    void prepareConfirmPastDueView();
}