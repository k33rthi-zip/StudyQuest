
package use_case.quit_study_session;

public interface QuitStudySessionOutputBoundary {

    void prepareSuccessView(
            QuitStudySessionOutputData outputData
    );

    void prepareFailView(String error);
}
