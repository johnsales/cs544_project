package cs544.team7.project.service;

import cs544.team7.project.model.Email;

import javax.mail.MessagingException;

public interface IEmailService {
	void sendMessage(Email e) throws MessagingException;
}
