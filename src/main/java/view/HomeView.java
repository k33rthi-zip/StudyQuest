package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomeView extends JPanel{

    public static final String VIEW_NAME = "home";

    private final JButton taskManagerButton = Y2KStyle.button("Task Manager");
    private final JButton shopButton = Y2KStyle.button("Shop");
    private final JButton logOutButton = Y2KStyle.compactButton("Log Out");

    private Runnable taskManagerAction = new Runnable() {
        @Override
        public void run() {
        }
    };
    private Runnable shopAction = new Runnable() {
        @Override
        public void run() {
        }
    };
    private Runnable logOutAction = new Runnable() {
        @Override
        public void run() {
        }
    };

    public HomeView() {
        setLayout(new BorderLayout());
        add(buildWindow(), BorderLayout.CENTER);

        taskManagerButton.addActionListener(event -> taskManagerAction.run());
        shopButton.addActionListener(event -> shopAction.run());
        logOutButton.addActionListener(event -> logOutAction.run());
        shopButton.setEnabled(false); //not implemented yet
    }

    public void setTaskManagerAction(Runnable action) {
        this.taskManagerAction = action;
    }
    public void setShopAction(Runnable action){
        this.shopAction = action;
        shopButton.setText("Shop");
        shopButton.setEnabled(true);
    }
    public void setLogOutAction(Runnable action){
        this.logOutAction = action;
    }

    private JPanel buildWindow() {
        final JPanel window = new JPanel(new BorderLayout());
        window.setBackground(Y2KStyle.SILVER);
        window.setBorder(BorderFactory.createCompoundBorder(Y2KStyle.raised(),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        window.setPreferredSize(new Dimension(960, 680));

        window.add(Y2KStyle.titleBar("StudyQuest - Home", logOutButton), BorderLayout.NORTH);
        window.add(buildMenu(), BorderLayout.CENTER);
        return window;
    }

    private JPanel buildMenu() {
        final JPanel grid = new JPanel(new GridLayout(0, 1, 0, 16));
        grid.setBackground(Y2KStyle.SILVER);
        grid.setBorder(BorderFactory.createEmptyBorder(30, 250, 30, 250));
        grid.add(taskManagerButton);
        grid.add(shopButton);

        final JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Y2KStyle.SILVER);
        section.setBorder(Y2KStyle.section("Main Menu"));
        section.add(grid, BorderLayout.CENTER);
        return section;
    }
}
