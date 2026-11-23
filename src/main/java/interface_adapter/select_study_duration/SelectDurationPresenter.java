
package interface_adapter.select_study_duration;
import use_case.select_study_duration.SelectDurationOutputBoundary;
import use_case.select_study_duration.SelectDurationOutputData;
import interface_adapter.study_mode.StudyModeState;
import interface_adapter.study_mode.StudyModeViewModel;

public class SelectDurationPresenter
        implements SelectDurationOutputBoundary {

    private final StudyModeViewModel viewModel;

    public SelectDurationPresenter(StudyModeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SelectDurationOutputData outputData) {
        StudyModeState state = viewModel.getState();

        state.setSelectedMinutes(outputData.getSelectedMinutes());
        state.setError(null);

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
