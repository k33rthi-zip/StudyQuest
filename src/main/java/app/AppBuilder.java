package app;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.util.ArrayList;

import data_access.InMemoryTaskDataAccessObject;
import interface_adapter.add_task.AddTaskController;
import interface_adapter.add_task.AddTaskPresenter;
import interface_adapter.add_task.AddTaskViewModel;
import use_case.add_task.AddTaskInputBoundary;
import use_case.add_task.AddTaskInteractor;
import use_case.add_task.AddTaskOutputBoundary;
import view.AddTaskView;
import view.HomeView;

import interface_adapter.view_account.ViewAccountViewModel;
import view.ProfileView;
import data_access.InMemoryViewAccountDataAccessObject;
import entity.User;
import entity.StudyCharacter;
import entity.Wardrobe;
import interface_adapter.view_account.ViewAccountController;
import interface_adapter.view_account.ViewAccountPresenter;
import use_case.view_account.ViewAccountInputBoundary;
import use_case.view_account.ViewAccountInteractor;
import use_case.view_account.ViewAccountOutputBoundary;
import use_case.view_account.ViewAccountUserDataAccessInterface;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final InMemoryTaskDataAccessObject taskDataAccessObject = new InMemoryTaskDataAccessObject();

    private HomeView homeView;
    private AddTaskViewModel addTaskViewModel;
    private AddTaskView addTaskView;
    private ViewAccountViewModel viewAccountViewModel;
    private ProfileView profileView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addHomeView() {
        homeView = new HomeView();
        homeView.setTaskManagerAction(() -> cardLayout.show(cardPanel, AddTaskViewModel.VIEW_NAME));
        //here call setUsername, setLogOutAction, setShopAction
        cardPanel.add(homeView, HomeView.VIEW_NAME);
        return this;
    }

    public AppBuilder addAddTaskView() {
        addTaskViewModel = new AddTaskViewModel();
        addTaskView = new AddTaskView(addTaskViewModel);
        cardPanel.add(addTaskView, AddTaskViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addProfileView() {
        viewAccountViewModel = new ViewAccountViewModel();
        profileView = new ProfileView(viewAccountViewModel);

        cardPanel.add(profileView, ViewAccountViewModel.VIEW_NAME);

        return this;
    }

    public AppBuilder addViewAccountUseCase() {
        // Temporary demo user for PP01.
        final User demoUser = new User(
                "demo_user",
                "demo_password",
                new StudyCharacter(100, new Wardrobe()),
                new ArrayList<>()
        );

        final ViewAccountUserDataAccessInterface userDataAccess =
                new InMemoryViewAccountDataAccessObject(demoUser);

        final ViewAccountOutputBoundary outputBoundary =
                new ViewAccountPresenter(viewAccountViewModel);

        final ViewAccountInputBoundary interactor =
                new ViewAccountInteractor(userDataAccess, outputBoundary);

        final ViewAccountController controller =
                new ViewAccountController(interactor);

        homeView.setProfileAction(() -> {
            controller.execute();
            cardLayout.show(cardPanel, ViewAccountViewModel.VIEW_NAME);
        });

        profileView.setBackToDashboardAction(() ->
                cardLayout.show(cardPanel, HomeView.VIEW_NAME));

        return this;
    }


    public AppBuilder addAddTaskUseCase() {
        final AddTaskOutputBoundary outputBoundary = new AddTaskPresenter(addTaskViewModel);
        final AddTaskInputBoundary interactor = new AddTaskInteractor(taskDataAccessObject, outputBoundary);
        addTaskView.setAddTaskController(new AddTaskController(interactor));
        addTaskView.setBackToDashboardAction(() -> cardLayout.show(cardPanel, HomeView.VIEW_NAME));
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("StudyQuest");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, HomeView.VIEW_NAME);
        return application;
    }
}