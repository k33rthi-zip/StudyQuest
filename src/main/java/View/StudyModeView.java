
package View;

import interface_adapter.select_study_duration.SelectDurationController;
import interface_adapter.study_mode.StudyModeState;
import interface_adapter.study_mode.StudyModeViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StudyModeView extends JPanel
        implements PropertyChangeListener {

    private final StudyModeViewModel viewModel;
    private final SelectDurationController controller;

    private final JComboBox<Integer> durationDropdown;
    private final JLabel selectedDurationLabel;

    public StudyModeView(
            StudyModeViewModel viewModel,
            SelectDurationController controller) {

        this.viewModel = viewModel;
        this.controller = controller;

        viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Study Mode");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instruction = new JLabel("Choose your study duration:");
        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);

        Integer[] durations = {15, 25, 30, 50, 60};

        durationDropdown = new JComboBox<>(durations);
        durationDropdown.setMaximumSize(new Dimension(200, 35));
        durationDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectedDurationLabel = new JLabel();
        selectedDurationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(20));
        add(instruction);
        add(Box.createVerticalStrut(10));
        add(durationDropdown);
        add(Box.createVerticalStrut(20));
        add(selectedDurationLabel);

        durationDropdown.setSelectedItem(
                viewModel.getState().getSelectedMinutes()
        );

        durationDropdown.addActionListener(event -> {
            Integer selectedMinutes =
                    (Integer) durationDropdown.getSelectedItem();

            if (selectedMinutes != null) {
                controller.execute(selectedMinutes);
            }
        });

        updateView();
    }

    private void updateView() {
        StudyModeState state = viewModel.getState();

        selectedDurationLabel.setText(
                "Selected duration: "
                        + state.getSelectedMinutes()
                        + " minutes"
        );

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getError(),
                    "Invalid Duration",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        updateView();
    }
}
