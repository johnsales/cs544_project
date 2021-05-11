
package cs544.team7.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cs544.team7.project.model.Session;
import cs544.team7.project.service.SessionService;

@RestController
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@PostMapping
	public Session createSession(@RequestBody Session session) {
		return sessionService.createSession(session);
	}
@GetMapping
	public List<Session> getAllSessions() {
		return sessionService.getAllSessions();
	}

  @DeleteMapping("/{id}")
 public Session deleteSession(@PathVariable (name="id") int id, Session session) {
	 return sessionService.deleteSession(id, session);
 }
  
  @PutMapping("/{id}")
  public Session updateSession(@PathVariable (name="id") int id, @RequestBody Session session) {
	  return sessionService.updateSession(id, session);
	  }
  
  @GetMapping("/{id}")
  public Session getSessionById(@PathVariable (name="id") int id) {
	  return sessionService.getSessionById(id);
	   }


}




