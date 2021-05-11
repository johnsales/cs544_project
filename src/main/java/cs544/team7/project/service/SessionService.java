package cs544.team7.project.service;

import cs544.team7.project.model.Session;
import cs544.team7.project.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService implements ISessionService {
    private SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }


    @Override
    public Session createSession(Session s) {
        return null;
    }

    @Override
    public List<Session> getAllSessions() {
        return null;
    }

    @Override
    public List<Session> getAllAvailableSessions() {
        return null;
    }

    @Override
    public Session getSessionById(int id) {
        return null;
    }

    @Override
    public Session updateSession(Session s) {
        return null;
    }

    @Override
    public Session deleteSession(Session s) {
        return null;
    }
}
