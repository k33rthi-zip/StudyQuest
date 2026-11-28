
package use_case.complete_study_session;

import entity.StudySession;
import entity.User;

public class CompleteStudySessionInteractor
        implements CompleteStudySessionInputBoundary {

    private final CompleteStudySessionDataAccessInterface
            sessionDataAccessObject;

    private final CompleteStudySessionUserDataAccessInterface
            userDataAccessObject;

    private final CompleteStudySessionOutputBoundary presenter;

    public CompleteStudySessionInteractor(
            CompleteStudySessionDataAccessInterface sessionDataAccessObject,
            CompleteStudySessionUserDataAccessInterface userDataAccessObject,
            CompleteStudySessionOutputBoundary presenter) {

        this.sessionDataAccessObject = sessionDataAccessObject;
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(CompleteStudySessionInputData inputData) {

        String username = inputData.getUsername();
        User user = userDataAccessObject.getCurrentUser();

        // Validate user
        if (user == null || username == null
                || !username.equals(user.getUsername())) {

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

        // Check whether the timer has finished
        if (!session.isFinished()) {
            presenter.prepareFailView(
                    "Your study session has not finished yet."
            );
            return;
        }

        // Complete session
        session.complete();

        // Calculate and award coins
        int coinsEarned = session.earnCoin();

        user.getCharacter().addCoins(coinsEarned);

        int totalCoins = user.getCharacter().getCoins();

        // Prepare output data
        CompleteStudySessionOutputData outputData =
                new CompleteStudySessionOutputData(
                        session.getMinutesCompleted(),
                        coinsEarned,
                        totalCoins
                );

        presenter.prepareSuccessView(outputData);
    }
}
