package app;
import data_access.InMemoryStudySessionDataAccessObject;

import interface_adapter.study_mode.StudyModeViewModel;

import interface_adapter.select_study_duration.SelectDurationController;
import interface_adapter.select_study_duration.SelectDurationPresenter;

import interface_adapter.start_study_session.StartStudySessionController;
import interface_adapter.start_study_session.StartStudySessionPresenter;

import use_case.select_study_duration.SelectDurationInteractor;
import use_case.start_study_session.StartStudySessionInteractor;

import View.StudyModeView;
import View.AddTaskView;
import View.HomeView;
import View.ProfileView;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.util.ArrayList;

import data_access.InMemoryCharacterDataAccessObject;
import data_access.InMemoryTaskDataAccessObject;
import interface_adapter.add_task.AddTaskController;
import interface_adapter.add_task.AddTaskPresenter;
import interface_adapter.task_list.AddTaskViewModel;
import interface_adapter.complete_task.CompleteTaskController;
import interface_adapter.complete_task.CompleteTaskPresenter;
import use_case.add_task.AddTaskInputBoundary;
import use_case.add_task.AddTaskInteractor;
import use_case.add_task.AddTaskOutputBoundary;

import use_case.complete_task.CompleteTaskInputBoundary;
import use_case.complete_task.CompleteTaskInteractor;
import use_case.complete_task.CompleteTaskOutputBoundary;
import view.AddTaskView;
import view.HomeView;

import interface_adapter.view_account.ViewAccountViewModel;
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
import interface_adapter.complete_study_session.CompleteSessionController;
import interface_adapter.complete_study_session.CompleteSessionPresenter;
import use_case.complete_study_session.CompleteStudySessionInteractor;
import interface_adapter.quit_study_session.QuitStudySessionController;
import interface_adapter.quit_study_session.QuitStudySessionPresenter;
import use_case.quit_study_session.QuitStudySessionInteractor;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final InMemoryTaskDataAccessObject taskDataAccessObject = new InMemoryTaskDataAccessObject();
    private final InMemoryStudySessionDataAccessObject
            studySessionDataAccessObject =
            new InMemoryStudySessionDataAccessObject();
    private final User demoUser = new User(
            "demo_user",
            "demo_password",
            new StudyCharacter(100, new Wardrobe()),
            new ArrayList<>()
    );

    private final InMemoryViewAccountDataAccessObject
            userDataAccessObject =
            new InMemoryViewAccountDataAccessObject(demoUser);
    // Placeholder character that holds the coins earned from completing tasks.
    // Swap this for the logged-in user's StudyCharacter once login is wired in.
    private final InMemoryCharacterDataAccessObject characterDataAccessObject =
            new InMemoryCharacterDataAccessObject(new StudyCharacter(0, new Wardrobe()));

    private HomeView homeView;
    private AddTaskViewModel addTaskViewModel;
    private StudyModeViewModel studyModeViewModel;
    private StudyModeView studyMode;
    private AddTaskView addTaskView;
    private ViewAccountViewModel viewAccountViewModel;
    private ProfileView profileView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addHomeView() {
        homeView = new HomeView();
        homeView.setTaskManagerAction(() -> cardLayout.show(cardPanel, AddTaskViewModel.VIEW_NAME));
        homeView.setStudyModeAction(() -> {

            int coins = userDataAccessObject
                    .getCurrentUser()
                    .getCharacter()
                    .getCoins();

            studyModeViewModel.getState().setCoins(coins);

            studyModeViewModel.firePropertyChanged();

            cardLayout.show(
                    cardPanel,
                    StudyModeViewModel.VIEW_NAME
            );
        });
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
        final ViewAccountOutputBoundary outputBoundary =
                new ViewAccountPresenter(viewAccountViewModel);

        final ViewAccountInputBoundary interactor =
                new ViewAccountInteractor(userDataAccessObject, outputBoundary);

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


    public AppBuilder addStudyModeView() {

        studyModeViewModel = new StudyModeViewModel();

        // SM01: Select Study Duration
        final SelectDurationPresenter selectPresenter =
                new SelectDurationPresenter(studyModeViewModel);

        final SelectDurationInteractor selectInteractor =
                new SelectDurationInteractor(selectPresenter);

        final SelectDurationController selectController =
                new SelectDurationController(selectInteractor);


        // SM02: Start Study Session
        final StartStudySessionPresenter startPresenter =
                new StartStudySessionPresenter(studyModeViewModel);

        final StartStudySessionInteractor startInteractor =
                new StartStudySessionInteractor(
                        studySessionDataAccessObject,
                        startPresenter
                );

        final StartStudySessionController startController =
                new StartStudySessionController(startInteractor);


        // SM03: Complete Study Session
        final CompleteSessionPresenter completePresenter =
                new CompleteSessionPresenter(studyModeViewModel);

        final CompleteStudySessionInteractor completeInteractor =
                new CompleteStudySessionInteractor(
                        studySessionDataAccessObject,
                        userDataAccessObject,
                        completePresenter
                );

        final CompleteSessionController completeController =
                new CompleteSessionController(completeInteractor);

        // SM04: Quit Study Session
        final QuitStudySessionPresenter quitPresenter =
                new QuitStudySessionPresenter(studyModeViewModel);

        final QuitStudySessionInteractor quitInteractor =
                new QuitStudySessionInteractor(
                        studySessionDataAccessObject,
                        quitPresenter
                );

        final QuitStudySessionController quitController =
                new QuitStudySessionController(quitInteractor);
        // Create Study Mode View
        studyMode = new StudyModeView(
                studyModeViewModel,
                selectController,
                startController,
                completeController,quitController
        );

        studyMode.setUsername(
                userDataAccessObject.getCurrentUser().getUsername()
        );

        studyMode.setBackToDashboardAction(() ->
                cardLayout.show(cardPanel, HomeView.VIEW_NAME)
        );

        cardPanel.add(
                studyMode,
                StudyModeViewModel.VIEW_NAME
        );

        return this;
    }




    public AppBuilder addAddTaskUseCase() {
        final AddTaskOutputBoundary outputBoundary = new AddTaskPresenter(addTaskViewModel);
        final AddTaskInputBoundary interactor = new AddTaskInteractor(taskDataAccessObject, outputBoundary);
        addTaskView.setAddTaskController(new AddTaskController(interactor));
        addTaskView.setBackToDashboardAction(() -> cardLayout.show(cardPanel, HomeView.VIEW_NAME));
        return this;
    }

    public AppBuilder addCompleteTaskUseCase() {
        final CompleteTaskOutputBoundary outputBoundary = new CompleteTaskPresenter(addTaskViewModel);
        final CompleteTaskInputBoundary interactor = new CompleteTaskInteractor(
                taskDataAccessObject, characterDataAccessObject, outputBoundary);
        addTaskView.setCompleteTaskController(new CompleteTaskController(interactor));
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