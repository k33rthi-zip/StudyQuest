package use_case.select_study_duration;

public interface SelectDurationOutputBoundary {
    void prepareSuccessView(SelectDurationOutputData outputData);

    void prepareFailView(String errorMessage);
}
