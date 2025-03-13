package live.learnjava.cachingsession.service;

import live.learnjava.cachingsession.model.UserSessionModel;

public interface IUserSessionService {
	
	void createSession(String sessionId, UserSessionModel sessionModel);
	UserSessionModel getSession(String sessionId);
	void deleteSession(String sessionId);
	String getUserInfo(String username);
	
}
