package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.view_account.ViewAccountState;
import interface_adapter.view_account.ViewAccountViewModel;
import interface_adapter.view_account.ViewAccountViewModelListener;

public class ProfileView extends JPanel
        implements ViewAccountViewModelListener {

    private final ViewAccountViewModel viewModel;

    private final JLabel usernameValue = Y2KStyle.label("-");
    private final JLabel coinsValue = Y2KStyle.label("-");
    private final JLabel statusLabel = Y2KStyle.label("Profile not loaded.");

    private final JButton backButton =
            Y2KStyle.button("Back to Dashboard >");

    private Runnable backToDashboardAction = () -> { };

    public ProfileView(ViewAccountViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addListener(this);

        setLayout(new BorderLayout(0, 8));
        setBackground(Y2KStyle.SILVER);

        add(Y2KStyle.titleBar("StudyQuest - Profile", backButton),
                BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 12, 10));
        infoPanel.setBackground(Y2KStyle.SILVER);
        infoPanel.setBorder(Y2KStyle.section("Account Information"));

        infoPanel.add(Y2KStyle.label("Username:"));
        infoPanel.add(usernameValue);
        infoPanel.add(Y2KStyle.label("Coins:"));
        infoPanel.add(coinsValue);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(Y2KStyle.SILVER);
        body.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        body.add(infoPanel, BorderLayout.NORTH);

        add(body, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        backButton.addActionListener(event ->
                backToDashboardAction.run());
    }

    public void setBackToDashboardAction(Runnable action) {
        this.backToDashboardAction = action;
    }

    @Override
    public void stateChanged() {
        ViewAccountState state = viewModel.getState();

        if (!state.getError().isEmpty()) {
            usernameValue.setText("-");
            coinsValue.setText("-");
            statusLabel.setText(state.getError());
            statusLabel.setForeground(Y2KStyle.ERROR_RED);
        }
        else {
            usernameValue.setText(state.getUsername());
            coinsValue.setText(Integer.toString(state.getCoins()));
            statusLabel.setText("Profile loaded.");
            statusLabel.setForeground(Color.BLACK);
        }
    }
}
