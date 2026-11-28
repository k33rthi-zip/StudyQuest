package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import interface_adapter.add_task.AddTaskController;
import interface_adapter.task_list.AddTaskState;
import interface_adapter.task_list.AddTaskViewModel;
import interface_adapter.task_list.AddTaskViewModelListener;
import interface_adapter.task_list.TaskRowFormatter;
import interface_adapter.complete_task.CompleteTaskController;
import interface_adapter.sort_tasks.SortTasksController;

public class AddTaskView extends JPanel implements AddTaskViewModelListener {

    private static final String FIRST_TASK_HINT = "Create your first task! Fill in the form and click Add Task.";

    private final AddTaskViewModel viewModel;
    private AddTaskController controller;

    private final JTextField titleField = new JTextField(22);
    private final JTextField dueField = new JTextField(10);
    private final JComboBox<String> priorityBox =
            new JComboBox<>(new String[] {"Low", "Mid", "High"});
    private final JTextArea noteArea = new JTextArea(3, 22);
    private final DefaultTableModel tableModel = new DefaultTableModel(TaskRowFormatter.COLUMN_NAMES, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable taskTable = new JTable(tableModel);
    private final JLabel statusLabel = Y2KStyle.label(FIRST_TASK_HINT);
    private final JButton addButton = Y2KStyle.button("Add Task");
    private final JButton completeButton = Y2KStyle.button("Complete Task");
    private final JComboBox<String> sortBox =
            new JComboBox<>(new String[] {SortTasksController.DUE_DATE, SortTasksController.PRIORITY});
    private final JButton backButton = Y2KStyle.compactButton("Back to Dashboard >");
    private Runnable backToDashboardAction = () -> { };
    private CompleteTaskController completeTaskController;
    private SortTasksController sortTasksController;
    private List<String> taskIds = new ArrayList<>();

    public AddTaskView(AddTaskViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addListener(this);

        setLayout(new BorderLayout());
        add(buildWindow(), BorderLayout.CENTER);

        backButton.addActionListener(event -> backToDashboardAction.run());
        addButton.addActionListener(event -> {
            submit(false);
            keepSortOrder();
        });
        sortBox.addActionListener(event -> {
            if (sortTasksController != null) {
                sortTasksController.execute((String) sortBox.getSelectedItem(), true);
            }
        });
        completeButton.addActionListener(event -> {
            final int selected = taskTable.getSelectedRow();
            if (selected < 0) {
                setStatus("Select a task in the Task List first.", true);
            }
            else {
                completeTaskController.execute(taskIds.get(selected));
                keepSortOrder();
            }
        });
    }

    public void setAddTaskController(AddTaskController controller) {
        this.controller = controller;
    }

    /**
     * Sets what the "Back to Dashboard" button does (the app wiring decides how to switch views).
     * @param action the code to run when the button is clicked
     */
    public void setBackToDashboardAction(Runnable action) {
        this.backToDashboardAction = action;
    }

    public void setCompleteTaskController(CompleteTaskController controller) {
        this.completeTaskController = controller;
    }

    public void setSortTasksController(SortTasksController controller) {
        this.sortTasksController = controller;
    }

    /**
     * Re-applies the chosen sort after a task is added or completed, without changing the status message.
     */
    private void keepSortOrder() {
        if (sortTasksController != null) {
            sortTasksController.execute((String) sortBox.getSelectedItem(), false);
        }
    }

    private JPanel buildWindow() {
        final JPanel window = new JPanel(new BorderLayout());
        window.setBackground(Y2KStyle.SILVER);
        window.setBorder(BorderFactory.createCompoundBorder(Y2KStyle.raised(),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        window.setPreferredSize(new Dimension(960, 680));

        window.add(Y2KStyle.titleBar("StudyQuest - TaskManager", backButton), BorderLayout.NORTH);

        final JPanel body = new JPanel(new BorderLayout(0, 8));
        body.setBackground(Y2KStyle.SILVER);
        body.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        body.add(buildForm(), BorderLayout.NORTH);
        body.add(buildList(), BorderLayout.CENTER);
        window.add(body, BorderLayout.CENTER);

        statusLabel.setFont(Y2KStyle.STATUS_FONT);
        statusLabel.setBorder(BorderFactory.createCompoundBorder(Y2KStyle.sunken(),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
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
        form.setBorder(Y2KStyle.section("New Task"));

        Y2KStyle.styleInput(titleField);
        Y2KStyle.styleInput(dueField);
        Y2KStyle.styleInput(noteArea);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        priorityBox.setFont(Y2KStyle.UI_FONT);
        priorityBox.setBackground(Color.WHITE);
        priorityBox.setSelectedIndex(1);

        addRow(form, 0, "Title:", titleField);
        addRow(form, 1, "Due date (YYYY-MM-DD):", dueField);
        addRow(form, 2, "Priority:", priorityBox);
        final JScrollPane noteScroll = new JScrollPane(noteArea);
        noteScroll.setBorder(null);
        addRow(form, 3, "Notes:", noteScroll);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttons.setBackground(Y2KStyle.SILVER);
        buttons.add(addButton);
        buttons.add(completeButton);
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 6, 6, 6);
        form.add(buttons, c);
        return form;
    }

    private void addRow(JPanel form, int row, String labelText, java.awt.Component field) {
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
        taskTable.setFont(Y2KStyle.LABEL_FONT);
        taskTable.setRowHeight(28);
        taskTable.setShowGrid(true);
        taskTable.setGridColor(new Color(160, 160, 160));
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskTable.setSelectionBackground(Y2KStyle.NAVY);
        taskTable.setSelectionForeground(Color.WHITE);
        taskTable.setFillsViewportHeight(true);
        taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        taskTable.getTableHeader().setReorderingAllowed(false);
        taskTable.getTableHeader().setResizingAllowed(false);
        taskTable.getTableHeader().setFont(Y2KStyle.SECTION_FONT);
        taskTable.getTableHeader().setBackground(Y2KStyle.SILVER);
        taskTable.getTableHeader().setBorder(Y2KStyle.raised());
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(340);

        final DefaultTableCellRenderer centered = new DefaultTableCellRenderer();
        centered.setHorizontalAlignment(SwingConstants.CENTER);
        taskTable.getColumnModel().getColumn(0).setCellRenderer(centered);
        taskTable.getColumnModel().getColumn(1).setCellRenderer(centered);

        final JScrollPane scroll = new JScrollPane(taskTable);
        scroll.setColumnHeaderView(taskTable.getTableHeader());
        scroll.setBorder(Y2KStyle.sunken());
        scroll.getViewport().setBackground(Color.WHITE);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Y2KStyle.SILVER);