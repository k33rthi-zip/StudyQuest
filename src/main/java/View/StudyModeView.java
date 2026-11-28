
package View;

import interface_adapter.select_study_duration.SelectDurationController;
import interface_adapter.start_study_session.StartStudySessionController;
import interface_adapter.study_mode.StudyModeState;
import interface_adapter.study_mode.StudyModeViewModel;
import interface_adapter.complete_study_session.CompleteSessionController;
import interface_adapter.quit_study_session.QuitStudySessionController;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.Instant;

public class StudyModeView extends JPanel
        implements PropertyChangeListener {

    private final StudyModeViewModel viewModel;

    private final SelectDurationController
            selectDurationController;

    private final StartStudySessionController
            startStudySessionController;
    private final CompleteSessionController
            completeSessionController;
    private final QuitStudySessionController
            quitStudySessionController;

    private final JComboBox<Integer> durationDropdown;
    private final JLabel timerLabel;
    private final JButton startButton;
    private final JButton quitButton;
    private final JLabel coinLabel = new JLabel("Coins: 0");
    private final JButton homeButton = new JButton("Home");

    private Runnable backToDashboardAction = () -> {};
    private final Timer countdownTimer;

    private String username;

    public StudyModeView(
            StudyModeViewModel viewModel,
            SelectDurationController selectDurationController,
            StartStudySessionController startStudySessionController,
            CompleteSessionController completeStudySessionController,
            QuitStudySessionController quitStudySessionController) {

        this.viewModel = viewModel;
        this.selectDurationController = selectDurationController;
        this.startStudySessionController =
                startStudySessionController;
        this.completeSessionController = completeStudySessionController;
        this.quitStudySessionController = quitStudySessionController;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 232, 208));

        // Title
        JLabel title = new JLabel("Study Mode");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Duration dropdown
        Integer[] durations = {15, 25, 30, 50, 60};

        durationDropdown = new JComboBox<>(durations);
        durationDropdown.setMaximumSize(
                new Dimension(200, 35)
        );
        durationDropdown.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        durationDropdown.setSelectedItem(
                viewModel.getState().getSelectedMinutes()
        );

        // Countdown label
        timerLabel = new JLabel("25:00");
        timerLabel.setFont(
                new Font("Arial", Font.BOLD, 48)
        );
        timerLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Start button
        startButton = new JButton("Start Session");
        startButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        // Quit button
        quitButton = new JButton("Quit Session");
        quitButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );
        quitButton.setVisible(false);

        quitButton.addActionListener(event -> confirmQuit());

        // Swing timer
        countdownTimer = new Timer(
                250, event -> updateCountdown()
        );

        // Dropdown listener
        durationDropdown.addActionListener(event -> {

            Integer selectedMinutes =
                    (Integer) durationDropdown.getSelectedItem();

            if (selectedMinutes != null) {
                selectDurationController.execute(
                        selectedMinutes
                );
            }
        });

        // Start button listener
        startButton.addActionListener(event -> {

            int selectedMinutes =
                    viewModel.getState().getSelectedMinutes();

            startStudySessionController.execute(
                    username, selectedMinutes
            );
        });

        // Add components

        add(buildHeader());
        add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(20));
        add(durationDropdown);
        add(Box.createVerticalStrut(30));
        add(timerLabel);
        add(Box.createVerticalStrut(20));
        add(startButton);
        add(quitButton);

        viewModel.addPropertyChangeListener(this);

        updateView();
    }

    private void confirmQuit() {

        if (!viewModel.getState().isSessionActive()) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?\n"
                        + "You will not earn any coins for this session.",
                "Quit Study Session",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        StudyModeState state = viewModel.getState();

        // The session may have completed while
        // the confirmation dialog was open.
        if (!state.isSessionActive()) {
            return;
        }

        // If the duration has elapsed, complete instead.
        if (state.getEndTime() != null
                && !Instant.now().isBefore(state.getEndTime())) {

            updateCountdown();
            return;
        }

        if (choice == JOptionPane.YES_OPTION) {

            quitStudySessionController.execute(username);
        }
    }

    private JPanel buildHeader() {

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(new Color(99, 75, 60));

        header.setBorder(
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        );

        header.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 60)
        );

        JLabel appTitle = new JLabel(
                "StudyQuest", SwingConstants.CENTER
        );

        appTitle.setForeground(new Color(245, 232, 208));
        appTitle.setFont(new Font("Monospaced", Font.BOLD, 20));

        coinLabel.setForeground(new Color(245, 204, 101));
        coinLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        homeButton.addActionListener(event -> {

            if (!viewModel.getState().isSessionActive()) {
                backToDashboardAction.run();
            }
        });

        header.add(homeButton, BorderLayout.WEST);
        header.add(appTitle, BorderLayout.CENTER);
        header.add(coinLabel, BorderLayout.EAST);

        return header;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setBackToDashboardAction(Runnable action) {
        this.backToDashboardAction = action;
    }

    private void updateCountdown() {

        StudyModeState state = viewModel.getState();

        if (!state.isSessionActive()
                || state.getEndTime() == null) {

            countdownTimer.stop();
            return;
        }

        Instant now = Instant.now();

        long remainingMillis = Duration.between(
                now,
                state.getEndTime()
        ).toMillis();

        long remainingSeconds = Math.max(
                0, (remainingMillis + 999) / 1000
        );

        state.setRemainingSeconds(remainingSeconds);

        displayTime(remainingSeconds);

        if (!now.isBefore(state.getEndTime())) {

            countdownTimer.stop();

            completeSessionController.execute(username);
        }
    }

    private void displayTime(long totalSeconds) {

        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        timerLabel.setText(
                String.format("%02d:%02d", minutes, seconds)
        );
    }

    private void updateView() {
        StudyModeState state = viewModel.getState();

        boolean active = state.isSessionActive();

        // Update coin balance
        coinLabel.setText("Coins: " + state.getCoins());

        // Prevent leaving during an active session
        homeButton.setEnabled(!active);

        durationDropdown.setEnabled(!active);
        startButton.setVisible(!active);
        quitButton.setVisible(active);
        quitButton.setEnabled(active);

        if (active) {

            if (!countdownTimer.isRunning()
                    && state.getRemainingSeconds() > 0) {

                updateCountdown();

                if (state.getRemainingSeconds() > 0) {
                    countdownTimer.start();
                }
            }

        } else {

            countdownTimer.stop();

            displayTime(
                    state.getSelectedMinutes() * 60L
            );
        }

        if (state.getError() != null) {

            String error = state.getError();
            state.setError(null);

            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Study Mode Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        if (state.getCompletionMessage() != null) {

            String message = state.getCompletionMessage();

            state.setCompletionMessage(null);

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Session Complete",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        if (state.getQuitMessage() != null) {

            String message = state.getQuitMessage();

            state.setQuitMessage(null);

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Session Ended",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        revalidate();
        repaint();
    }
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        updateView();
    }
}
