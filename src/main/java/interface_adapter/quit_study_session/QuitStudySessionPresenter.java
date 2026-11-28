
package interface_adapter.quit_study_session;

import interface_adapter.study_mode.StudyModeState;
import interface_adapter.study_mode.StudyModeViewModel;
import use_case.quit_study_session.QuitStudySessionOutputBoundary;
import use_case.quit_study_session.QuitStudySessionOutputData;

public class QuitStudySessionPresenter
        implements QuitStudySessionOutputBoundary {

    private final StudyModeViewModel viewModel;

    public QuitStudySessionPresenter(
            StudyModeViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            QuitStudySessionOutputData outputData) {

        StudyModeState state = viewModel.getState();

        state.setSessionActive(false);
        state.setRemainingSeconds(0);
        state.setEndTime(null);

        state.setError(null);
        state.setCompletionMessage(null);

        state.setQuitMessage(
                "Study session ended.\n"
                        + "You studied for "
                        + outputData.getMinutesCompleted()
                        + " out of "
                        + outputData.getPlannedMinutes()
                        + " minutes.\n"
                        + "No coins were earned."
        );

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {

        StudyModeState state = viewModel.getState();

        state.setQuitMessage(null);
        state.setError(error);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
