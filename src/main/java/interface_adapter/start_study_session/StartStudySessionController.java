
package interface_adapter.start_study_session;

import use_case.start_study_session.StartStudySessionInputBoundary;
import use_case.start_study_session.StartStudySessionInputData;

public class StartStudySessionController {

    private final StartStudySessionInputBoundary interactor;

    public StartStudySessionController(
            StartStudySessionInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username, int plannedMinutes) {

        StartStudySessionInputData inputData =
                new StartStudySessionInputData(
                        username, plannedMinutes
                );

        interactor.execute(inputData);
    }
}
