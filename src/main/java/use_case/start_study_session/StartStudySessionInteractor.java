
package use_case.start_study_session;

import entity.StudySession;
import java.util.Set;

public class StartStudySessionInteractor
        implements StartStudySessionInputBoundary {

    private final StartStudySessionDataAccessInterface dataAccess;
    private final StartStudySessionOutputBoundary presenter;

    private static final Set<Integer> ALLOWED_DURATIONS =
            Set.of(15, 25, 30, 50, 60);

    public StartStudySessionInteractor(
            StartStudySessionDataAccessInterface dataAccess,
            StartStudySessionOutputBoundary presenter) {

        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(StartStudySessionInputData inputData) {

        String username = inputData.getUsername();
        int plannedMinutes = inputData.getPlannedMinutes();

        if (username == null || username.isBlank()) {
            presenter.prepareFailView(
                    "You must be logged in to study."
            );
            return;
        }

        if (!ALLOWED_DURATIONS.contains(plannedMinutes)) {
            presenter.prepareFailView(
                    "Invalid study duration."
            );
            return;
        }

        if (dataAccess.hasActiveSession(username)) {
            presenter.prepareFailView(
                    "You already have an active study session."
            );
            return;
        }

        StudySession session = new StudySession(plannedMinutes);

        dataAccess.saveSession(username, session);

        StartStudySessionOutputData outputData =
                new StartStudySessionOutputData(
                        session.getPlannedMinutes(),
                        session.getRemainingSeconds(),
                        session.getStartTime().plusSeconds(
                                session.getPlannedMinutes() * 60L
                        )
                );

        presenter.prepareSuccessView(outputData);
    }
}
