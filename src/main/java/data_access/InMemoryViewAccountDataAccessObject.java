package data_access;

import entity.User;
import use_case.view_account.ViewAccountUserDataAccessInterface;

public class InMemoryViewAccountDataAccessObject
        implements ViewAccountUserDataAccessInterface {

    private User currentUser;

    public InMemoryViewAccountDataAccessObject(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
