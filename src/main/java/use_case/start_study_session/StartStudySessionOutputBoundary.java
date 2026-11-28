
package use_case.start_study_session;

public interface StartStudySessionOutputBoundary {

    void prepareSuccessView(
            StartStudySessionOutputData outputData);

    void prepareFailView(String errorMessage);
}
