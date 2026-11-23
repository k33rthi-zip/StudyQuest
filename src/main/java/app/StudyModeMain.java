
import interface_adapter.select_study_duration.SelectDurationController;
import interface_adapter.select_study_duration.SelectDurationPresenter;
import interface_adapter.study_mode.StudyModeViewModel;

import use_case.select_study_duration.SelectDurationInputBoundary;
import use_case.select_study_duration.SelectDurationInteractor;
import use_case.select_study_duration.SelectDurationOutputBoundary;

import View.StudyModeView;

import javax.swing.*;

public class StudyModeMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // 1. Create the ViewModel
            StudyModeViewModel viewModel = new StudyModeViewModel();

            // 2. Create the Presenter
            SelectDurationOutputBoundary presenter =
                    new SelectDurationPresenter(viewModel);

            // 3. Create the Interactor
            SelectDurationInputBoundary interactor =
                    new SelectDurationInteractor(presenter);

            // 4. Create the Controller
            SelectDurationController controller =
                    new SelectDurationController(interactor);

            // 5. Create the Swing View
            StudyModeView studyModeView =
                    new StudyModeView(viewModel, controller);

            // 6. Display the window
            JFrame frame = new JFrame("StudyQuest - Study Mode");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(studyModeView);
            frame.setSize(450, 350);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
