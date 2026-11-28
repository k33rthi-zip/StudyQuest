
package interface_adapter.quit_study_session;

import use_case.quit_study_session.QuitStudySessionInputBoundary;
import use_case.quit_study_session.QuitStudySessionInputData;

public class QuitStudySessionController {

    private final QuitStudySessionInputBoundary interactor;

    public QuitStudySessionController(
            QuitStudySessionInputBoundary interactor) {

        this.interactor = interactor;
    }

    public void execute(String username) {

        QuitStudySessionInputData inputData =
                new QuitStudySessionInputData(username);

        interactor.execute(inputData);
    }
}
