
package interface_adapter.start_study_session;

import interface_adapter.study_mode.StudyModeState;
import interface_adapter.study_mode.StudyModeViewModel;
import use_case.start_study_session.StartStudySessionOutputBoundary;
import use_case.start_study_session.StartStudySessionOutputData;

public class StartStudySessionPresenter
        implements StartStudySessionOutputBoundary {

    private final StudyModeViewModel viewModel;

    public StartStudySessionPresenter(
            StudyModeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            StartStudySessionOutputData outputData) {

        StudyModeState state = viewModel.getState();

        state.setSelectedMinutes(
                outputData.getPlannedMinutes()
        );

        state.setRemainingSeconds(
                outputData.getRemainingSeconds()
        );

        state.setSessionActive(true);
        state.setError(null);
        state.setEndTime(outputData.getEndTime());
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {

        StudyModeState state = viewModel.getState();

        state.setError(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
