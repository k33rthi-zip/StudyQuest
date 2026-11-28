
package use_case.quit_study_session;

import entity.StudySession;

import java.time.Duration;
import java.time.Instant;

public class QuitStudySessionInteractor
        implements QuitStudySessionInputBoundary {

    private final QuitStudySessionDataAccessInterface
            sessionDataAccessObject;

    private final QuitStudySessionOutputBoundary presenter;

    public QuitStudySessionInteractor(
            QuitStudySessionDataAccessInterface sessionDataAccessObject,
            QuitStudySessionOutputBoundary presenter) {

        this.sessionDataAccessObject = sessionDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(QuitStudySessionInputData inputData) {

        String username = inputData.getUsername();

        // Validate username
        if (username == null || username.isBlank()) {
            presenter.prepareFailView(
                    "You must be logged in."
            );
            return;
        }

        // Retrieve existing session
        StudySession session =
                sessionDataAccessObject.getSession(username);

        // Validate session
        if (session == null
                || !"In Progress".equals(session.getStatus())) {

            presenter.prepareFailView(
                    "No active study session found."
            );
            return;
        }

        // Prevent quitting a finished session
        if (session.isFinished()) {
            presenter.prepareFailView(
                    "Your study session has already finished."
            );
            return;
        }

        // Calculate elapsed minutes
        int minutesElapsed = (int) Math.max(
                0,
                Duration.between(
                        session.getStartTime(),
                        Instant.now()
                ).toMinutes()
        );

        // Quit the session
        session.quit(minutesElapsed);

        // Prepare output data
        QuitStudySessionOutputData outputData =
                new QuitStudySessionOutputData(
                        session.getPlannedMinutes(),
                        session.getMinutesCompleted()
                );

        presenter.prepareSuccessView(outputData);
    }
}
