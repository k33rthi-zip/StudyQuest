
package use_case.quit_study_session;

import entity.StudySession;

public interface QuitStudySessionDataAccessInterface {

    StudySession getSession(String username);
}
