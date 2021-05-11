package cs544.team7.project.service;

import java.time.LocalDate;
import java.util.List;

import cs544.team7.project.model.Session;

public interface ISessionService {
	public Session createSession(Session session);
	public List<Session> getAllSessions();
	public List<Session> getAllAvailableSessions();
	public Session getSessionById(int id);
	public Session updateSession(int id, Session session);
	public Session deleteSession(int id, Session session);
}
