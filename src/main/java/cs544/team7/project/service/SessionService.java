package cs544.team7.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs544.team7.project.model.Session;
import cs544.team7.project.repository.SessionRepository;

@Service
public class SessionService implements ISessionService {

	@Autowired
	private SessionRepository sessionRepository;

	public Session createSession(Session session) {
		sessionRepository.findById(session.getId()).ifPresent(s -> {
			throw new RuntimeException();
		});

		return sessionRepository.save(session);
	}

	public List<Session> getAllSessions() {
		return sessionRepository.findAll();
	}

	public List<Session> getAllAvailableSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Session getSessionById(int id) {
		return sessionRepository.findById(id).get();
	}

	public Session updateSession(int id, Session session) {
		sessionRepository.findById(id).orElseThrow(RuntimeException::new);
		if (id == session.getId()) {
			return sessionRepository.save(session);
		}
		return null;
	}

	public Session deleteSession(int id, Session session) {
		sessionRepository.findById(id).orElseThrow(RuntimeException::new);
		if (id == session.getId()) {
			sessionRepository.deleteById(id);
		}
		return session;
	}

}
