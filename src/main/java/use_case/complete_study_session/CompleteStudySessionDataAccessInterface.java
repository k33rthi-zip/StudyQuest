package use_case.complete_study_session;

import entity.StudySession;

public interface CompleteStudySessionDataAccessInterface {

    StudySession getSession(String username);
}