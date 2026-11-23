package use_case.view_account;

public interface ViewAccountOutputBoundary {
    void prepareSuccessView(ViewAccountOutputData outputData);

    void prepareFailView(String errorMessage);
}
