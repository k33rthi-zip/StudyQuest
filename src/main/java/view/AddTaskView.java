package view;

import java.awt.*;
import javax.swing.*;
import interface_adapter.add_task.AddTaskController;
import interface_adapter.add_task.AddTaskState;
import interface_adapter.add_task.AddTaskViewModel;
import interface_adapter.add_task.AddTaskViewModelListener;

public class AddTaskView extends JPanel implements AddTaskViewModelListener {

    private final AddTaskViewModel viewModel;
    private AddTaskController controller;

    private final JTextField titleField = new JTextField(22);
    private final JTextField dueField = new JTextField(10);
    private final JComboBox<String> priorityBox =
            new JComboBox<>(new String[] {"1 - Low", "2 - Mid", "3 - High"});
    private final JTextArea noteArea = new JTextArea(3, 22);
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> taskList = new JList<>(listModel);
    private final JLabel statusLabel = Y2KStyle.label("Ready.");
    private final JButton addButton = Y2KStyle.button("Add Task");
    private final JButton cancelButton = Y2KStyle.button("Cancel");

    public AddTaskView(AddTaskViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addListener(this);

        setBackground(Y2KStyle.TEAL);
        setLayout(new GridBagLayout());
        add(buildWindow());

        addButton.addActionListener(event -> submit(false));
        cancelButton.addActionListener(event -> {
            clearForm();
            setStatus("Cancelled. No task was added.", false);
        });
    }

    public void setAddTaskController(AddTaskController controller) {
        this.controller = controller;
    }

    private JPanel buildWindow() {
        final JPanel window = new JPanel(new BorderLayout());
        window.setBackground(Y2KStyle.SILVER);
        window.setBorder(BorderFactory.createCompoundBorder(Y2KStyle.raised(),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        window.setPreferredSize(new Dimension(560, 600));

        window.add(Y2KStyle.titleBar("StudyQuest - My Tasks.exe"), BorderLayout.NORTH);

        final JPanel body = new JPanel(new BorderLayout(0, 8));
        body.setBackground(Y2KStyle.SILVER);
        body.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        body.add(buildForm(), BorderLayout.NORTH);
        body.add(buildList(), BorderLayout.CENTER);
        window.add(body, BorderLayout.CENTER);

        statusLabel.setBorder(BorderFactory.createCompoundBorder(Y2KStyle.sunken(),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        final JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(Y2KStyle.SILVER);
        statusBar.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        statusBar.add(statusLabel, BorderLayout.CENTER);
        window.add(statusBar, BorderLayout.SOUTH);
        return window;
    }

    private JPanel buildForm() {
        final JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Y2KStyle.SILVER);
        form.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "New Task"));

        Y2KStyle.styleInput(titleField);
        Y2KStyle.styleInput(dueField);
        Y2KStyle.styleInput(noteArea);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        priorityBox.setFont(Y2KStyle.UI_FONT);
        priorityBox.setBackground(Color.WHITE);
        priorityBox.setSelectedIndex(1);

        addRow(form, 0, "Title:", titleField);
        addRow(form, 1, "Due (YYYY-MM-DD):", dueField);
        addRow(form, 2, "Priority:", priorityBox);
        final JScrollPane noteScroll = new JScrollPane(noteArea);
        noteScroll.setBorder(null);
        addRow(form, 3, "Notes:", noteScroll);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        buttons.setBackground(Y2KStyle.SILVER);
        buttons.add(addButton);
        buttons.add(cancelButton);
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 6, 6, 6);
        form.add(buttons, c);
        return form;
    }

    private void addRow(JPanel form, int row, String labelText, Component field) {
        final GridBagConstraints left = new GridBagConstraints();
        left.gridx = 0;
        left.gridy = row;
        left.anchor = GridBagConstraints.NORTHEAST;
        left.insets = new Insets(5, 6, 3, 6);
        form.add(Y2KStyle.label(labelText), left);

        final GridBagConstraints right = new GridBagConstraints();
        right.gridx = 1;
        right.gridy = row;
        right.weightx = 1;
        right.fill = GridBagConstraints.HORIZONTAL;
        right.insets = new Insets(3, 0, 3, 6);
        form.add(field, right);
    }

    private JPanel buildList() {
        taskList.setFont(Y2KStyle.MONO_FONT);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setSelectionBackground(Y2KStyle.NAVY);
        taskList.setSelectionForeground(Color.WHITE);

        final JScrollPane scroll = new JScrollPane(taskList);
        scroll.setBorder(Y2KStyle.sunken());

        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Y2KStyle.SILVER);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Upcoming Tasks"));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private void submit(boolean confirmPastDue) {
        controller.execute(titleField.getText(), dueField.getText(),
                priorityBox.getSelectedIndex() + 1, noteArea.getText(), confirmPastDue);
    }

    private void clearForm() {
        titleField.setText("");
        dueField.setText("");
        noteArea.setText("");
        priorityBox.setSelectedIndex(1);
    }

    private void setStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setForeground(isError ? Y2KStyle.ERROR_RED : Color.BLACK);
    }

    @Override
    public void stateChanged() {
        final AddTaskState state = viewModel.getState();

        if (state.isConfirmPastDueRequested()) {
            final int choice = JOptionPane.showConfirmDialog(this,
                    "That due date is in the past. Add the task anyway?",
                    "StudyQuest", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                submit(true);
            }
            else {
                setStatus("Task not added. Change the due date and try again.", false);
            }
            return;
        }

        listModel.clear();
        listModel.addAll(state.getUpcomingTasks());

        if (!state.getError().isEmpty()) {
            setStatus("Error: " + state.getError(), true);
        }
        else if (state.isTaskAdded()) {
            clearForm();
            setStatus("Task added.", false);
        }
    }
}