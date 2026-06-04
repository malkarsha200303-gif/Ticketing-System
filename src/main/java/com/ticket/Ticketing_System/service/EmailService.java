package com.ticket.Ticketing_System.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // Email sent to agent when new ticket is created
    public void sendTicketCreatedEmailToAgent(String agentEmail,
                                               String ticketNumber,
                                               String userName,
                                               String subject,
                                               String description) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(agentEmail);
        message.setSubject("New Ticket: " + ticketNumber);
        message.setText(
            "A new support ticket has been submitted.\n\n" +
            "Ticket Number : " + ticketNumber + "\n" +
            "From          : " + userName + "\n" +
            "Subject       : " + subject + "\n" +
            "Description   : " + description + "\n\n" 
           
        );
        mailSender.send(message);
    }

    // Email sent to user when ticket is resolved
    public void sendTicketResolvedEmailToUser(String userEmail,
                                               String ticketNumber,
                                               String userName,
                                               String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(userEmail);
        message.setSubject("Your Ticket " + ticketNumber + " is Resolved!");
        message.setText(
            "Dear " + userName + ",\n\n" +
            "Great news! Your support ticket has been resolved.\n\n" +
            "Ticket Number : " + ticketNumber + "\n" +
            "Subject       : " + subject + "\n" +
            "Status        : RESOLVED\n\n" +
            "Thank you for your patience.\n" +
            "Support Team"
        );
        mailSender.send(message);
    }
}