package use_case.view_account;

import entity.User;

public class ViewAccountInteractor implements ViewAccountInputBoundary {

    private final ViewAccountUserDataAccessInterface userDataAccess;
    private final ViewAccountOutputBoundary outputBoundary;

    public ViewAccountInteractor(
            ViewAccountUserDataAccessInterface userDataAccess,
            ViewAccountOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute() {
        User user = userDataAccess.getCurrentUser();

        if (user == null) {
            outputBoundary.prepareFailView("No user is currently logged in.");
            return;
        }

        String username = user.getUsername();
        int coins = user.getCharacter().getCoins();

        ViewAccountOutputData outputData =
                new ViewAccountOutputData(username, coins);

        outputBoundary.prepareSuccessView(outputData);
    }
}
