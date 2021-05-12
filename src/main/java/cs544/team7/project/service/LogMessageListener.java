package cs544.team7.project.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
@Log4j2
public class LogMessageListener implements MessageListener {

    @JmsListener(destination = "log")
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                log.info(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }
}
