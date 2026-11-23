package interface_adapter.view_account;

import use_case.view_account.ViewAccountInputBoundary;

public class ViewAccountController {

    private final ViewAccountInputBoundary interactor;

    public ViewAccountController(ViewAccountInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}
