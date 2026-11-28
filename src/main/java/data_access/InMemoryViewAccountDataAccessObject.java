package data_access;

import entity.User;
import use_case.view_account.ViewAccountUserDataAccessInterface;
import use_case.complete_study_session
        .CompleteStudySessionUserDataAccessInterface;

public class InMemoryViewAccountDataAccessObject
        implements ViewAccountUserDataAccessInterface,
        CompleteStudySessionUserDataAccessInterface{

    private User currentUser;

    public InMemoryViewAccountDataAccessObject(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
