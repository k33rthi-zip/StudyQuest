
package interface_adapter.select_study_duration;

import use_case.select_study_duration.SelectDurationInputBoundary;
import use_case.select_study_duration.SelectDurationInputData;

public class SelectDurationController {

    private final SelectDurationInputBoundary interactor;

    public SelectDurationController(
            SelectDurationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(int selectedMinutes) {

        SelectDurationInputData inputData =
                new SelectDurationInputData(selectedMinutes);

        interactor.execute(inputData);
    }
}
