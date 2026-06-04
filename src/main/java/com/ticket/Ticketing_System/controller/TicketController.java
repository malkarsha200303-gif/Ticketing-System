package com.ticket.Ticketing_System.controller;

import com.ticket.Ticketing_System.model.Ticket;
import com.ticket.Ticketing_System.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // POST /api/tickets — User submits a complaint
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket created = ticketService.createTicket(ticket);
        return ResponseEntity.ok(created);
    }

    // GET /api/tickets — Agent gets all tickets
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // GET /api/tickets/open — Agent gets only OPEN tickets
    @GetMapping("/open")
    public ResponseEntity<List<Ticket>> getOpenTickets() {
        return ResponseEntity.ok(ticketService.getOpenTickets());
    }

    // PUT /api/tickets/{id}/resolve — Agent resolves a ticket
    @PutMapping("/{id}/resolve")
    public ResponseEntity<Ticket> resolveTicket(@PathVariable Long id) {
        Ticket resolved = ticketService.resolveTicket(id);
        return ResponseEntity.ok(resolved);
    }
}