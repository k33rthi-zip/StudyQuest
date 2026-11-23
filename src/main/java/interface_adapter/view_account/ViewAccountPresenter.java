package interface_adapter.view_account;

import use_case.view_account.ViewAccountOutputBoundary;
import use_case.view_account.ViewAccountOutputData;

public class ViewAccountPresenter implements ViewAccountOutputBoundary {

    private final ViewAccountViewModel viewModel;

    public ViewAccountPresenter(ViewAccountViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewAccountOutputData outputData) {
        ViewAccountState state = viewModel.getState();

        state.setUsername(outputData.getUsername());
        state.setCoins(outputData.getCoins());
        state.setError("");

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        ViewAccountState state = viewModel.getState();

        state.setUsername("");
        state.setCoins(0);
        state.setError(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
