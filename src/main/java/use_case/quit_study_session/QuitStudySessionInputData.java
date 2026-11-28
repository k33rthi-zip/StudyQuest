
package use_case.quit_study_session;

public class QuitStudySessionInputData {

    private final String username;

    public QuitStudySessionInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
