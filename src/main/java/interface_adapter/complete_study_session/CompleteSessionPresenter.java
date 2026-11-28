
package interface_adapter.complete_study_session;

import interface_adapter.study_mode.StudyModeState;
import interface_adapter.study_mode.StudyModeViewModel;
import use_case.complete_study_session.CompleteStudySessionOutputBoundary;
import use_case.complete_study_session.CompleteStudySessionOutputData;

public class CompleteSessionPresenter
        implements CompleteStudySessionOutputBoundary {

    private final StudyModeViewModel viewModel;

    public CompleteSessionPresenter(
            StudyModeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            CompleteStudySessionOutputData outputData) {

        StudyModeState state = viewModel.getState();

        state.setSessionActive(false);
        state.setRemainingSeconds(0);
        state.setEndTime(null);

        state.setCoins(outputData.getTotalCoins());

        state.setError(null);

        state.setCompletionMessage(
                "Session complete!\n"
                        + "You studied for "
                        + outputData.getMinutesCompleted()
                        + " minutes.\n"
                        + "You earned "
                        + outputData.getCoinsEarned()
                        + " coins!"
        );

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

        StudyModeState state = viewModel.getState();

        state.setCompletionMessage(null);
        state.setError(error);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
