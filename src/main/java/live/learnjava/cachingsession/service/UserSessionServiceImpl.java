package live.learnjava.cachingsession.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import live.learnjava.cachingsession.model.UserSessionModel;

@Service
public class UserSessionServiceImpl implements IUserSessionService {
	//use template
	
	@Autowired
	private RedisTemplate<String, Object> template;
	
	private static final String SESSION_PREFIX = "SESSION:" ;
	
	@Override
	public void createSession(String sessionId, UserSessionModel sessionModel) {
		template.opsForValue().set(SESSION_PREFIX+sessionId, sessionModel);
		
	}

	@Override
	public UserSessionModel getSession(String sessionId) {
		System.out.println(SESSION_PREFIX+sessionId);
		return (UserSessionModel)template.opsForValue().get(SESSION_PREFIX+sessionId);
	}

	@Override
	public void deleteSession(String sessionId) {
		template.delete(SESSION_PREFIX+sessionId);		
	}

	/*this method below is for demostrating the caching capabilities of Redis. The above
	 * three methods are to show how we can manage user session data for fast retrival using redis server. 
	*/
	@Override
	@Cacheable(value="users", key="#username")
	public String getUserInfo(String username) {
		try {
			Thread.sleep(3000); //simulate slow DB call
		}catch(InterruptedException  e) {
			e.printStackTrace();
		}
		
		return "User info for: " + username;
	}

}
