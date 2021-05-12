package cs544.team7.project.service;

import cs544.team7.project.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(Person person, String message) {
        jmsTemplate.send("log", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(
                        "To: " + person.getEmail() + ", message: " + message
                );
            }
        });
    }
}
