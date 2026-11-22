package app;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryTaskDataAccessObject;
import interface_adapter.add_task.AddTaskController;
import interface_adapter.add_task.AddTaskPresenter;
import interface_adapter.add_task.AddTaskViewModel;
import use_case.add_task.AddTaskInputBoundary;
import use_case.add_task.AddTaskInteractor;
import use_case.add_task.AddTaskOutputBoundary;
import view.AddTaskView;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final InMemoryTaskDataAccessObject taskDataAccessObject = new InMemoryTaskDataAccessObject();

    private AddTaskViewModel addTaskViewModel;
    private AddTaskView addTaskView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addAddTaskView() {
        addTaskViewModel = new AddTaskViewModel();
        addTaskView = new AddTaskView(addTaskViewModel);
        cardPanel.add(addTaskView, AddTaskViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addAddTaskUseCase() {
        final AddTaskOutputBoundary outputBoundary = new AddTaskPresenter(addTaskViewModel);
        final AddTaskInputBoundary interactor = new AddTaskInteractor(taskDataAccessObject, outputBoundary);
        addTaskView.setAddTaskController(new AddTaskController(interactor));
        addTaskView.setBackToDashboardAction(() -> cardLayout.show(cardPanel, "dashboard"));
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("StudyQuest");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, AddTaskViewModel.VIEW_NAME);
        return application;
    }
}