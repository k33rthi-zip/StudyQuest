
package use_case.start_study_session;

import entity.StudySession;

public interface StartStudySessionDataAccessInterface {

    boolean hasActiveSession(String username);

    void saveSession(String username, StudySession session);
}
