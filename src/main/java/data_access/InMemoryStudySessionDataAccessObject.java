
package data_access;

import entity.StudySession;
import use_case.start_study_session.StartStudySessionDataAccessInterface;
import use_case.complete_study_session
        .CompleteStudySessionDataAccessInterface;
import use_case.quit_study_session.QuitStudySessionDataAccessInterface;
import java.util.HashMap;
import java.util.Map;

public class InMemoryStudySessionDataAccessObject
        implements StartStudySessionDataAccessInterface,
        CompleteStudySessionDataAccessInterface, QuitStudySessionDataAccessInterface  {

    private final Map<String, StudySession> sessions =
            new HashMap<>();

    @Override
    public boolean hasActiveSession(String username) {

        StudySession session = sessions.get(username);

        return session != null
                && session.getStatus().equals("In Progress");
    }

    @Override
    public void saveSession(
            String username, StudySession session) {

        sessions.put(username, session);
    }
    @Override
    public StudySession getSession(String username) {
        return sessions.get(username);
    }
}
