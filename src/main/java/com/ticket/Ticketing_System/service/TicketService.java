package com.ticket.Ticketing_System.service;

import com.ticket.Ticketing_System.model.Ticket;
import com.ticket.Ticketing_System.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EmailService emailService;

    @Value("${agent.email}")
    private String agentEmail;

    // 1. Create a new ticket
    public Ticket createTicket(Ticket ticket) {

        long count = ticketRepository.count() + 1;
        ticket.setTicketNumber("TKT-" + (1000 + count));

        Ticket savedTicket = ticketRepository.save(ticket);

        emailService.sendTicketCreatedEmailToAgent(
            agentEmail,
            savedTicket.getTicketNumber(),
            savedTicket.getUserName(),
            savedTicket.getSubject(),
            savedTicket.getDescription()
        );

        return savedTicket;
    }

    // 2. Get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // 3. Get only OPEN tickets
    public List<Ticket> getOpenTickets() {
        return ticketRepository.findByStatus("OPEN");
    }

    // 4. Resolve a ticket
    public Ticket resolveTicket(Long id) {
        Optional<Ticket> optional = ticketRepository.findById(id);

        if (optional.isPresent()) {
            Ticket ticket = optional.get();
            ticket.setStatus("RESOLVED");
            ticket.setResolvedAt(LocalDateTime.now());

            Ticket resolvedTicket = ticketRepository.save(ticket);

            emailService.sendTicketResolvedEmailToUser(
                resolvedTicket.getUserEmail(),
                resolvedTicket.getTicketNumber(),
                resolvedTicket.getUserName(),
                resolvedTicket.getSubject()
            );

            return resolvedTicket;

        } else {
            throw new RuntimeException("Ticket not found with ID: " + id);
        }
    }
}