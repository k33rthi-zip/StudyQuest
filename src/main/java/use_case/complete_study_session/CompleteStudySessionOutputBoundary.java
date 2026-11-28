
package use_case.complete_study_session;

public interface CompleteStudySessionOutputBoundary {

    void prepareSuccessView(
            CompleteStudySessionOutputData outputData
    );

    void prepareFailView(String error);
}
