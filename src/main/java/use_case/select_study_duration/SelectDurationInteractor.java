package use_case.select_study_duration;
import java.util.Set;


public class SelectDurationInteractor implements SelectDurationInputBoundary {
    private final SelectDurationOutputBoundary presenter;

    private static final Set<Integer> ALLOWED_DURATIONS =
            Set.of(15, 25, 30, 50, 60);

    public SelectDurationInteractor(
            SelectDurationOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(SelectDurationInputData inputData) {

        int selectedMinutes = inputData.getSelectedMinutes();

        if (!ALLOWED_DURATIONS.contains(selectedMinutes)) {
            presenter.prepareFailView(
                    "Please select a valid study duration."
            );
            return;
        }

        SelectDurationOutputData outputData =
                new SelectDurationOutputData(selectedMinutes);

        presenter.prepareSuccessView(outputData);
    }

}
