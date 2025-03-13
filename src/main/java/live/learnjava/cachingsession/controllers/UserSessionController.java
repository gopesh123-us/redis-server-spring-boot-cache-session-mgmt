package live.learnjava.cachingsession.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import live.learnjava.cachingsession.model.UserSessionModel;
import live.learnjava.cachingsession.service.IUserSessionService;

@RestController
@RequestMapping("/api/v1")
public class UserSessionController {
	//use service
	
	@Autowired
	private IUserSessionService service;
	
	@PostMapping("/createSession/{sessionId}")
	public ResponseEntity<String> createSession(@PathVariable String sessionId, @RequestBody UserSessionModel model) {
		service.createSession(sessionId, model);
		String success = "Session created!";
		return new ResponseEntity<String>(success, HttpStatus.OK);
	}
	
	@GetMapping("/getSession/{sessionId}")
	public ResponseEntity<UserSessionModel> getSession(@PathVariable String sessionId) {
		UserSessionModel model = service.getSession(sessionId);
		return new ResponseEntity<UserSessionModel>(model, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteSession/{sessionId}")
	public ResponseEntity<String> deleteSession(@PathVariable String sessionId){
		service.deleteSession(sessionId);
		return new ResponseEntity<String>("UserSession with sessionId: " + sessionId + " deleted", HttpStatus.OK);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<String> getUserInfo(@PathVariable String username) {
		String userInfo = service.getUserInfo(username);
		return new ResponseEntity<String>(userInfo, HttpStatus.OK);
	}
}
