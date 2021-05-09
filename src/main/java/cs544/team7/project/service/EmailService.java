package cs544.team7.project.service;

import cs544.team7.project.model.Person;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailService implements IEmailService {
    @Override
    public void sendMessage(Person p, String message) {
       log.log(Level.INFO, "Sent to " + p.getEmail() + " , message = " +  message);
    }
}
