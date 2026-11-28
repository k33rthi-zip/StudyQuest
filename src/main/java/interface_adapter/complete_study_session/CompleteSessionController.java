
package interface_adapter.complete_study_session;

import use_case.complete_study_session.CompleteStudySessionInputBoundary;
import use_case.complete_study_session.CompleteStudySessionInputData;

public class CompleteSessionController {

    private final CompleteStudySessionInputBoundary interactor;

    public CompleteSessionController(
            CompleteStudySessionInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username) {

        CompleteStudySessionInputData inputData =
                new CompleteStudySessionInputData(username);

        interactor.execute(inputData);
    }
}
